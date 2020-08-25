package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.repository.CargaPedidoRepository;

@Service
public class CargaPedidoServiceImpl implements ICargaPedidoService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CargaPedidoRepository repository;

	@Override
	public List<ComercialPedidoInformacion> findAll() {
		// TODO Auto-generated method stub
		return (List<ComercialPedidoInformacion>) repository.findAll();
	}

	@Override
	public void save(ComercialPedidoInformacion pedido) {
		// TODO Auto-generated method stub
		repository.save(pedido);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);

	}

	@Override
	public ComercialPedidoInformacion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> CargaPedidoVista(Long iduser) {
		List<Object[]> re = null;
		if (iduser != null) {
			re = em.createNativeQuery("SELECT\r\n" + 
					"	informacion.id_pedido_informacion,\r\n" + 
					"	informacion.id_text,\r\n" + 
					"	cliente.nombre,\r\n" + 
					"	IFNULL( DATE( informacion.fecha_entrega ), 'Por definir' ),\r\n" + 
					"	cliente.id_cliente,\r\n" + 
					"	informacion.observacion,\r\n" + 
					"	(\r\n" + 
					"	SELECT\r\n" + 
					"		IFNULL( sum( coorpre.monto_adicional ), 0 ) \r\n" + 
					"	FROM\r\n" + 
					"		alt_comercial_coordinado_prenda coorpre\r\n" + 
					"		INNER JOIN alt_comercial_coordinado coor ON coorpre.id_coordinado = coor.id_coordinado\r\n" + 
					"		INNER JOIN alt_comercial_pedido_informacion pedinf ON coor.id_pedido = pedinf.id_pedido_informacion \r\n" + 
					"	WHERE\r\n" + 
					"		pedinf.id_pedido_informacion = informacion.id_pedido_informacion \r\n" + 
					"	) AS cargoPrecio,\r\n" + 
					"	(\r\n" + 
					"	SELECT\r\n" + 
					"		IFNULL( sum( mtrz.descuento ), 0 ) \r\n" + 
					"	FROM\r\n" + 
					"		alt_comercial_total_razon_social mtrz\r\n" + 
					"		INNER JOIN alt_comercial_pedido_informacion pedinf2 ON mtrz.id_pedido = pedinf2.id_pedido_informacion \r\n" + 
					"	WHERE\r\n" + 
					"		pedinf2.id_pedido_informacion = informacion.id_pedido_informacion \r\n" + 
					"	) AS desceunto \r\n" + 
					"FROM\r\n" + 
					"	alt_comercial_pedido_informacion informacion\r\n" + 
					"	INNER JOIN alt_comercial_cliente cliente ON informacion.id_empresa = cliente.id_cliente AND informacion.id_usuario = "+ iduser +" \r\n" + 
					"GROUP BY\r\n" + 
					"	informacion.id_pedido_informacion \r\n" + 
					"ORDER BY\r\n" + 
					"	informacion.fecha_creacion DESC").getResultList();
		} else {
			re = em.createNativeQuery("SELECT\r\n" + 
					"	informacion.id_pedido_informacion,\r\n" + 
					"	informacion.id_text,\r\n" + 
					"	cliente.nombre,\r\n" + 
					"	IFNULL( DATE( informacion.fecha_entrega ), 'Por definir' ),\r\n" + 
					"	cliente.id_cliente,\r\n" + 
					"	informacion.observacion,\r\n" + 
					"	montos_razon(informacion.id_pedido_informacion)\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"FROM\r\n" + 
					"	alt_comercial_pedido_informacion informacion\r\n" + 
					"	INNER JOIN alt_comercial_cliente cliente ON informacion.id_empresa = cliente.id_cliente \r\n" + 
					"GROUP BY\r\n" + 
					"	informacion.id_pedido_informacion \r\n" + 
					"ORDER BY\r\n" + 
					"	informacion.fecha_creacion DESC").getResultList();
		}

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> PedidosExistenteIdEmpresa(Long id) {
		List<Object[]> re = em.createNativeQuery(
				"select informacion.id_pedido_informacion, informacion.id_text  from alt_comercial_pedido_informacion as informacion \r\n"
						+ "where informacion.id_empresa=" + id)
				.getResultList();
		return re;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String ValidarCantidadEspecial(Long id){
		String errores = null;
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	prenda.descripcion_prenda,\n" + 
				"	SUM( concen.cantidad_especial ) AS sum \n" + 
				"FROM\n" + 
				"	alt_comercial_pedido_informacion AS pedido,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_pre,\n" + 
				"	alt_comercial_concetrado_prenda AS concen,\n" + 
				"	alt_disenio_prenda AS prenda \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
				"	AND coor.id_coordinado = coor_pre.id_coordinado \n" + 
				"	AND coor_pre.id_coordinado_prenda = concen.id_coordinado_prenda \n" + 
				"	AND concen.estatus = 1 \n" + 
				"	AND prenda.id_prenda = coor_pre.id_prenda \n" + 
				"	AND pedido.id_pedido_informacion = "+id+" \n" + 
				"GROUP BY\n" + 
				"	concen.id_coordinado_prenda")
				.getResultList();
		Float valor;
		for (Object[] a : re) {
			valor = Float.parseFloat(a[1].toString());
			if ( valor >0 && valor <5 ) {
				errores=(a[0].toString() +"\n");
			}
		}
		return errores;
	}
}

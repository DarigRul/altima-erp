package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialPrendaBordado;
import com.altima.springboot.app.repository.ComercialPrendaBordadoRepository;

@Service
public class ComercialPrendaBordadoServiceImpl implements IComercialPrendaBordadoService {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialPrendaBordadoRepository repository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialPrendaBordado> findAll(Long id) {
		return em.createQuery("from ComercialPrendaBordado  where id_coordinado_prenda ="+id).getResultList();
	
	}

	@Override
	public void save(ComercialPrendaBordado ComercialCliente) {
		repository.save(ComercialCliente);
		
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public ComercialPrendaBordado findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllCoordinado(Long id) {
	
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	coor_prenda.id_coordinado_prenda,\n" + 
				"	coor.numero_coordinado,\n" + 
				"	look.nombre_lookup,\n" + 
				"	prenda.descripcion_prenda,\n" + 
				"	tela.nombre_tela,\n" + 
				"	look2.nombre_lookup AS look2,\n" + 
				"	tela.color,\n" + 
				"	( SELECT IFNULL( SUM( bordado.precio_bordado ), 0 ) FROM alt_comercial_prenda_bordado AS bordado WHERE bordado.id_coordinado_prenda = coor_prenda.id_coordinado_prenda ),\n" + 
				"	coor_prenda.precio,\n" + 
				"	coor_prenda.adicional,\n" + 
				"	coor_prenda.monto_adicional,\n" + 
				"	coor_prenda.precio_final,\n" + 
				"	coor_prenda.observaciones \n" + 
				"FROM\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_disenio_tela AS tela,\n" + 
				"	alt_disenio_lookup AS look,\n" + 
				"	alt_disenio_lookup AS look2,\n" + 
				"	alt_comercial_pedido_informacion AS pedido \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor.id_coordinado = coor_prenda.id_coordinado \n" + 
				"	AND coor_prenda.id_prenda = prenda.id_prenda \n" + 
				"	AND prenda.id_familia_prenda = look.id_lookup \n" + 
				"	AND coor_prenda.id_tela = tela.id_tela \n" + 
				"	AND tela.id_familia_composicion = look2.id_lookup \n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
				"	AND coor.id_pedido = "+id+" \n" + 
				"	AND coor_prenda.estatus = 1 \n" + 
				"ORDER BY\n" + 
				"	coor.numero_coordinado,\n" + 
				"	prenda.descripcion_prenda").getResultList();
		return re;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Float sumBordados(Long id) {
		
		String re = em.createNativeQuery("SELECT IFNULL( SUM( bordado.precio_bordado ) , 0)\r\n" + 
				"				FROM \r\n" + 
				"				alt_comercial_prenda_bordado AS bordado  \r\n" + 
				"				WHERE bordado.id_coordinado_prenda="+id).getSingleResult().toString();
		
		
			return Float.parseFloat(re); 
			
	}
	
	@Transactional(readOnly = true)
	@Override
	public Float precio_coor_prenda(Long id) {
		
		String re = em.createNativeQuery("select \r\n" + 
				"CASE\r\n" + 
				"						WHEN pedido.precio_usar = 1 THEN\r\n" + 
				"						precio.precio_local_nuevo  \r\n" + 
				"						WHEN pedido.precio_usar = 2 THEN \r\n" + 
				"						precio.precio_local_antiguo \r\n" + 
				"						WHEN pedido.precio_usar = 3 THEN \r\n" + 
				"						precio.precio_foraneo_nuevo \r\n" + 
				"						WHEN pedido.precio_usar = 4 THEN\r\n" + 
				"						precio.precio_foraneo_antiguo ELSE '000'\r\n" + 
				"					END\r\n" + 
				"from \r\n" + 
				"alt_disenio_lista_precio_prenda as precio, \r\n" + 
				"alt_comercial_coordinado as coor,\r\n" + 
				"alt_comercial_coordinado_prenda  coor_prenda,\r\n" + 
				"alt_comercial_pedido_informacion AS pedido\r\n" + 
				"where 1=1\r\n" + 
				"and coor_prenda.id_coordinado = coor.id_coordinado\r\n" + 
				"and coor.id_pedido = pedido.id_pedido_informacion\r\n" + 
				"and precio.id_prenda = coor_prenda.id_prenda\r\n" + 
				"and coor_prenda.id_coordinado_prenda="+id).getSingleResult().toString();
		  return Float.parseFloat(re); 
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> BordadosView(Long id) {
		
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	bordado.id_bordado,\n" + 
				"	bordado.descripcion \n" + 
				"FROM\n" + 
				"	alt_comercial_bordado AS bordado,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_pre,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_pedido_informacion AS pedido \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND bordado.estatus_bordado = 1 \n" + 
				"	AND bordado.estatus = 1 \n" + 
				"	AND coor_pre.id_coordinado = coor.id_coordinado \n" + 
				"	AND coor.id_pedido = pedido.id_pedido_informacion \n" + 
				"	AND pedido.id_empresa = bordado.id_cliente \n" + 
				"	AND id_coordinado_prenda = "+id+" \n").getResultList();
		return re;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Float precioBordado(Long id) {
		
		String re = em.createNativeQuery("SELECT\n" + 
				"	look.atributo_1\n" + 
				"FROM\n" + 
				"	alt_comercial_bordado AS bordado,\n" + 
				"	alt_comercial_lookup AS look \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND bordado.id_lookup = look.id_lookup \n" + 
				"	AND bordado.id_bordado="+id).getSingleResult().toString();
		
		
			return Float.parseFloat(re); 
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllDescipcion(Long id) {
		
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	pb.id_prenda_bordado,\n" + 
				"	pb.precio_bordado,\n" + 
				"	bordado.descripcion,\n" + 
				"	pb.archivo_bordado \n" + 
				"FROM\n" + 
				"	alt_comercial_bordado AS bordado,\n" + 
				"	alt_comercial_prenda_bordado AS pb \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND pb.id_bordado = bordado.id_bordado \n" + 
				"	AND pb.id_coordinado_prenda = "+id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> CambioPrecio(Long id) {
		
		List<Object[]> re = em.createNativeQuery("SELECT\n" + 
				"	coor_prenda.id_coordinado_prenda,\n" + 
				"	coor.numero_coordinado,\n" + 
				"	look.nombre_lookup,\n" + 
				"	prenda.descripcion_prenda,\n" + 
				"	tela.nombre_tela,\n" + 
				"	look2.nombre_lookup as look2 ,\n" + 
				"	tela.color,\n" + 
				"	(SELECT  IFNULL(SUM( bordado.precio_bordado ) , 0)  FROM alt_comercial_prenda_bordado AS bordado WHERE bordado.id_coordinado_prenda = coor_prenda.id_coordinado_prenda ),\n" + 
				"CASE\n" + 
				"	WHEN\n" + 
				"		pedido.precio_usar = 1 THEN\n" + 
				"			precio.precio_local_nuevo \n" + 
				"			WHEN pedido.precio_usar = 2 THEN\n" + 
				"			precio.precio_local_antiguo \n" + 
				"			WHEN pedido.precio_usar = 3 THEN\n" + 
				"			precio.precio_foraneo_nuevo \n" + 
				"			WHEN pedido.precio_usar = 4 THEN\n" + 
				"			precio.precio_foraneo_antiguo \n" + 
				"			WHEN pedido.precio_usar = 5 THEN\n" + 
				"			precio.precio_linea_express_local_nuevo \n" + 
				"			WHEN pedido.precio_usar = 6 THEN\n" + 
				"			precio.precio_linea_express_local_anterior \n" + 
				"			WHEN pedido.precio_usar = 7 THEN\n" + 
				"			precio.precio_linea_express_foraneo_nuevo \n" + 
				"			WHEN pedido.precio_usar = 8 THEN\n" + 
				"			precio.precio_linea_express_foraneo_anterior \n" + 
				"			WHEN pedido.precio_usar = 9 THEN\n" + 
				"			precio.precio_muestrario \n" + 
				"			WHEN pedido.precio_usar = 10 THEN\n" + 
				"			precio.precio_venta_interna \n" + 
				"			WHEN pedido.precio_usar = 11 THEN\n" + 
				"			precio.precio_e_commerce \n" + 
				"			WHEN pedido.precio_usar = 12 THEN\n" + 
				"			precio.precio_extra_1 \n" + 
				"			WHEN pedido.precio_usar = 13 THEN\n" + 
				"			precio.precio_extra_2 \n" + 
				"			WHEN pedido.precio_usar = 14 THEN\n" + 
				"			precio.precio_extra_3 ELSE '000' \n" + 
				"		END precio ,"+
				"	coor_prenda.adicional,\n" + 
				"	coor_prenda.monto_adicional,\n" + 
				"	coor_prenda.precio_final,\n" + 
				"	coor_prenda.observaciones \n" + 
				"FROM\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_disenio_tela AS tela,\n" + 
				"	alt_disenio_lookup AS look,\n" + 
				"	alt_disenio_lookup AS look2,\n" + 
				"	alt_comercial_pedido_informacion AS pedido,\n" + 
				"    alt_disenio_lista_precio_prenda as precio\n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor.id_coordinado = coor_prenda.id_coordinado \n" + 
				"	AND coor_prenda.id_prenda = prenda.id_prenda \n" + 
				"	AND prenda.id_familia_prenda = look.id_lookup \n" + 
				"	AND coor_prenda.id_tela = tela.id_tela \n" + 
				"	AND tela.id_familia_composicion = look2.id_lookup \n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
				"   AND precio.id_prenda = coor_prenda.id_prenda \n" +
				"   AND precio.id_familia_composicion = tela.id_familia_composicion \n" +
				"	AND coor.id_pedido = "+id+" \n" + 
				"	AND coor_prenda.estatus = 1 \n" + 
				"ORDER BY\n" + 
				"	coor.numero_coordinado,prenda.descripcion_prenda").getResultList();
		return re;
	}
	
}
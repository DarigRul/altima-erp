package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
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
			 
			
			re = em.createNativeQuery(""
					+ "SELECT\n" + 
					"						informacion.id_pedido_informacion, \n" + 
					"						informacion.id_text, \n" + 
					"						cliente.nombre,\n" + 
					"						IFNULL( DATE( informacion.fecha_entrega ), 'Por definir' ),\n" + 
					"						cliente.id_cliente, \n" + 
					"						informacion.observacion,\n" + 
					"						montos_razon(informacion.id_pedido_informacion), \n" + 
					"						if (informacion.estatus=1 , '1','2')  \n" + 
					"					 \n" + 
					"					FROM\n" + 
					"						alt_comercial_pedido_informacion informacion\n" + 
					"						INNER JOIN alt_comercial_cliente cliente ON informacion.id_empresa = cliente.id_cliente \n" + 
					"						WHERE\n" + 
					"						1=1\n" + 
					"						AND  informacion.id_usuario = "+iduser+" \n" + 
					"					GROUP BY\n" + 
					"						informacion.id_pedido_informacion \n" + 
					"					ORDER BY\n" + 
					"						informacion.fecha_creacion DESC").getResultList();
		} else {
			re = em.createNativeQuery(""
					+ "SELECT\n" + 
					"	informacion.id_pedido_informacion,\n" + 
					"IF\n" + 
					"	( config.tipo_pedido = 1, informacion.id_text, CONCAT( informacion2.id_text, '-', informacion.id_text ) ),\n" + 
					"	cliente.nombre,\n" + 
					"	IFNULL( DATE( informacion.fecha_entrega ), 'Por definir' ),\n" + 
					"	cliente.id_cliente,\n" + 
					"	informacion.observacion,\n" + 
					"	montos_razon ( informacion.id_pedido_informacion ),\n" + 
					"IF\n" + 
					"	( informacion.estatus = 1, '1', '2' ),\n" + 
					"	informacion.fecha_toma_tallas,\n" + 
					"	config.tipo_pedido \n" + 
					"FROM\n" + 
					"	alt_comercial_pedido_informacion informacion\n" + 
					"	INNER JOIN alt_comercial_cliente cliente ON informacion.id_empresa = cliente.id_cliente\n" + 
					"	INNER JOIN alt_admin_configuracion_pedido config ON informacion.tipo_pedido = config.id_configuracion_pedido,\n" + 
					"	alt_comercial_pedido_informacion informacion2 \n" + 
					"WHERE\n" + 
					"	1 = 1 \n" + 
					"	AND ( informacion.id_pedido IS NULL || informacion.id_pedido = informacion2.id_pedido_informacion ) \n" + 
					"GROUP BY\n" + 
					"	informacion.id_pedido_informacion \n" + 
					"ORDER BY\n" + 
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
		List<String> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	prenda.descripcion_prenda \n" + 
				"FROM\n" + 
				"	alt_comercial_pedido_informacion AS pedido,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_pre,\n" + 
				"	alt_comercial_concetrado_prenda AS concen,\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_admin_configuracion_pedido AS c \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
				"	AND coor.id_coordinado = coor_pre.id_coordinado \n" + 
				"	AND coor_pre.id_coordinado_prenda = concen.id_coordinado_prenda \n" + 
				"	AND concen.cantidad_especial > 0 \n" + 
				"	AND concen.estatus = 1 \n" + 
				"	AND prenda.id_prenda = coor_pre.id_prenda \n" + 
				"	AND c.id_configuracion_pedido = pedido.tipo_pedido \n" + 
				"	AND pedido.id_pedido_informacion = "+id+" \n" + 
				"GROUP BY\n" + 
				"	concen.id_coordinado_prenda \n" + 
				"HAVING\n" + 
				"	SUM( concen.cantidad_especial ) < (\n" + 
				"	SELECT\n" + 
				"		c.min_adicionales \n" + 
				"	FROM\n" + 
				"		alt_admin_configuracion_pedido AS c,\n" + 
				"		alt_comercial_pedido_informacion AS pedido \n" + 
				"	WHERE\n" + 
				"		1 = 1 \n" + 
				"		AND pedido.tipo_pedido = c.id_configuracion_pedido \n" + 
				"	AND pedido.id_pedido_informacion = "+id+" \n" + 
				"	)")
				.getResultList();
		
		if ( re.isEmpty()) {
			return null;
		}else {
			return "Piezas adiccionales faltantes";
		}
		
		
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean validarBordado(Long id){
		String re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	 COUNT(bordado.precio_bordado)\n" + 
				"FROM\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_pedido_informacion AS pedido ,\n" + 
				"	alt_comercial_prenda_bordado AS bordado \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor.id_coordinado = coor_prenda.id_coordinado \n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
				"	AND coor.id_pedido = "+id+" \n" + 
				"	AND coor_prenda.estatus = 1 \n" + 
				"	and bordado.id_coordinado_prenda = coor_prenda.id_coordinado_prenda").getSingleResult().toString();
		if ( re.equals("0")) {
			return false;
		}else {
			return true;
		}
		
		
	}
	
	@Override
	@Transactional
	public Integer validarPiezas(Long id){
		
		String re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"\n" + 
				"	SUM(concen.cantidad)\n" + 
				"	\n" + 
				"FROM\n" + 
				"	alt_comercial_pedido_informacion AS pedido,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_pre,\n" + 
				"	alt_comercial_concetrado_prenda AS concen,\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_admin_configuracion_pedido AS c \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
				"	AND coor.id_coordinado = coor_pre.id_coordinado \n" + 
				"	AND coor_pre.id_coordinado_prenda = concen.id_coordinado_prenda \n" + 
				"\n" + 
				"	AND concen.estatus = 1 \n" + 
				"	AND prenda.id_prenda = coor_pre.id_prenda \n" + 
				"	AND c.id_configuracion_pedido = pedido.tipo_pedido \n" + 
				"	AND pedido.id_pedido_informacion = "+id+" \n" + 
				"GROUP BY\n" + 
				"	pedido.id_pedido_informacion")
				.getSingleResult().toString();
		
		double d = Double.parseDouble(re); 
		return (int) d;
		
		
		
	}
	@Override
	@Transactional
	public String validarMonto(Long id){
	
		try {
			String re = em.createNativeQuery("SELECT\n" + 
					"CASE\n" + 
					"		\n" + 
					"	WHEN\n" + 
					"		cliente.foraneo = 0 THEN\n" + 
						"		IF\n" + 
						"			( SUM( concen.cantidad * coor_pre.precio_final ) >= c.locales, 'Todo bien', 'No cumple el monto total' ) \n" + 
					"			WHEN cliente.foraneo = 1 THEN\n" + 
					"		IF\n" + 
					"			( SUM( concen.cantidad * coor_pre.precio_final ) >= c.foraneo, 'Todo bien', 'No cumple el monto total' ) ELSE 'Error'\n" + 
					"		END \n" + 
					"		END \n" + 
					"		FROM\n" + 
					"			alt_comercial_pedido_informacion AS pedido,\n" + 
					"			alt_comercial_coordinado AS coor,\n" + 
					"			alt_comercial_coordinado_prenda AS coor_pre,\n" + 
					"			alt_comercial_concetrado_prenda AS concen,\n" + 
					"			alt_disenio_prenda AS prenda,\n" + 
					"			alt_admin_configuracion_pedido AS c,\n" + 
					"			alt_comercial_cliente AS cliente \n" + 
					"		WHERE\n" + 
					"			1 = 1 \n" + 
					"			AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
					"			AND coor.id_coordinado = coor_pre.id_coordinado \n" + 
					"			AND coor_pre.id_coordinado_prenda = concen.id_coordinado_prenda \n" + 
					"			AND concen.estatus = 1 \n" + 
					"			AND prenda.id_prenda = coor_pre.id_prenda \n" + 
					"			AND c.id_configuracion_pedido = pedido.tipo_pedido \n" + 
					"			AND cliente.id_cliente = pedido.id_empresa \n" + 
					"			AND pedido.id_pedido_informacion = "+id+" \n" + 
					"	GROUP BY\n" + 
					"	pedido.id_pedido_informacion")
					.getSingleResult().toString();
			return  re;
			}
			catch(Exception e) {
				
				return "Error , no es posible calcular el monto";
			}
		

		
		
	}
	@Override
	@Transactional
	public AdminConfiguracionPedido findOneConfig(String tipo) {
		AdminConfiguracionPedido result = null;
		result =  (AdminConfiguracionPedido) em
				.createQuery(
						"from AdminConfiguracionPedido where id_configuracion_pedido=" + tipo ).getSingleResult();
		
		return result;
	}

	@Override
	public String CalcularFecha(String fecha, Integer dias) {
		String re = em.createNativeQuery(""
				+ "SELECT alt_sumar_dias_habiles('"+fecha+"', "+dias+")")
				.getSingleResult().toString();
		
		return re;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> listPedidos(){
		List<Object[]> re = null;

			 
			
			re = em.createNativeQuery(""
					+ "SELECT\n" + 
					"	con.id_configuracion_pedido,\n" + 
					"	con.nombre\n" + 
					"FROM\n" + 
					"	alt_admin_configuracion_pedido AS con\n" + 
					"	where 1=1\n" + 
					"	and con.estatus =1 ").getResultList();

			
		return re;
	}
	
	
	@Override
	@Transactional
	public String validarStock(Long id){
	
		try {
			String re = em.createNativeQuery(""
					+ "SELECT\n" + 
					"	IF ( SUM( concen.cantidad )  >= c.minimo_personas, 'Correcto', 'No cumple con el stock' ) \n" + 
					"FROM\n" + 
					"	alt_comercial_pedido_informacion AS pedido,\n" + 
					"	alt_comercial_coordinado AS coor,\n" + 
					"	alt_comercial_coordinado_prenda AS coor_pre,\n" + 
					"	alt_comercial_concetrado_prenda AS concen,\n" + 
					"	alt_disenio_prenda AS prenda,\n" + 
					"	alt_admin_configuracion_pedido AS c,\n" + 
					"	alt_comercial_cliente_empleado AS empleado \n" + 
					"WHERE\n" + 
					"	1 = 1 \n" + 
					"	AND pedido.id_pedido_informacion = coor.id_pedido \n" + 
					"	AND coor.id_coordinado = coor_pre.id_coordinado \n" + 
					"	AND coor_pre.id_coordinado_prenda = concen.id_coordinado_prenda \n" + 
					"	AND concen.estatus = 1 \n" + 
					"	AND prenda.id_prenda = coor_pre.id_prenda \n" + 
					"	AND c.id_configuracion_pedido = pedido.tipo_pedido \n" + 
					"	AND empleado.id_empleado = concen.id_empleado \n" + 
					"	AND empleado.nombre_empleado LIKE \"SPF%\" \n" + 
					"	AND pedido.id_pedido_informacion = "+id+" \n" + 
					"GROUP BY\n" + 
					"	pedido.id_pedido_informacion")
					.getSingleResult().toString();
			return  re;
			}
			catch(Exception e) {
				
				return "Error , no es posible calcular el stock";
			}
		

		
		
	}
	
}

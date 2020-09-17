package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialMovimiento;
import com.altima.springboot.app.repository.ComercialMovimientoRepository;

@Service
@SuppressWarnings("unchecked")
public class ComercialMovimientoServiceImpl implements IComercialMovimientoService {

	@Autowired
	private ComercialMovimientoRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional
	public void save(ComercialMovimiento movimiento) {
		repository.save(movimiento);
	}
	
	@Override
	@Transactional
	public ComercialMovimiento findOne(Long id) {
		
		return em.find(ComercialMovimiento.class, id);
	}

	@Override
	@Transactional
	public List<Object> listarMuestras() {
		return em.createNativeQuery("select prenda.descripcion_prenda, pedido.talla, telas.nombre_tela, pedido.id_detalle_pedido, prenda.id_text AS text, telas.id_text from alt_produccion_detalle_pedido pedido" + 
									"	INNER JOIN alt_disenio_prenda prenda ON pedido.id_prenda = prenda.id_prenda" + 
									"	INNER JOIN alt_disenio_tela telas ON pedido.id_tela = telas.id_tela" +
									"   INNER JOIN alt_disenio_lista_precio_prenda precio ON prenda.id_prenda = precio.id_prenda AND telas.id_familia_composicion = precio.id_familia_composicion" +	
									"   WHERE pedido.estatus_confeccion=2 AND pedido.estatus=1").getResultList();
	}
	
	@Override
	@Transactional
	public Object EncontrarMuestra(Long id) {
		return em.createNativeQuery("select prenda.descripcion_prenda, pedido.talla, telas.nombre_tela, pedido.id_detalle_pedido, prenda.id_text AS text, telas.id_text, prenda.id_prenda, telas.id_tela from alt_produccion_detalle_pedido pedido" + 
									"	INNER JOIN alt_disenio_prenda prenda ON pedido.id_prenda = prenda.id_prenda" + 
									"	INNER JOIN alt_disenio_tela telas ON pedido.id_tela = telas.id_tela" +
									"   WHERE pedido.id_detalle_pedido ="+id).getSingleResult();
	}
	
	@Override
	@Transactional
	public List<Object> findAllWithNames(){
		return em.createNativeQuery("SELECT movimiento.id_movimiento, \n" + 
											"movimiento.id_text, \n" + 
											"cliente.nombre,\n" + 
											"cliente.apellido_paterno, \n" + 
											"cliente.apellido_materno, \n" + 
											"empleado.nombre_persona, \n" + 
											"empleado.apellido_paterno as paterno, \n" + 
											"empleado.apellido_materno as materno, \n" + 
											"movimiento.fecha_salida, \n" + 
											"movimiento.fecha_entrega, \n" + 
											"movimiento.estatus, \n" + 
											"(SELECT SUM(precio.precio_muestrario) \n" + 
											" FROM alt_comercial_movimiento_muestra_detalle as muest\n" + 
											"	INNER JOIN alt_disenio_prenda prenda ON muest.modelo_prenda = prenda.id_prenda\n" +
											"   INNER JOIN alt_disenio_tela tela ON muest.codigo_tela = tela.id_tela\n" +
											"	INNER JOIN alt_disenio_lista_precio_prenda precio ON muest.modelo_prenda = precio.id_prenda " +
											 													"AND tela.id_familia_composicion = precio.id_familia_composicion\n" + 
											"	WHERE muest.id_movimiento = movimiento.id_movimiento\n" +
											"   AND (muest.estatus=1 OR muest.estatus=2 OR muest.estatus=6 OR muest.estatus=7)) AS Total, movimiento.encargado\n" + 
									"from alt_comercial_movimiento as movimiento\n" + 
									"	INNER JOIN alt_comercial_cliente cliente ON movimiento.empresa = id_cliente\n" + 
									"	INNER JOIN alt_hr_empleado empleado ON movimiento.vendedor = empleado.id_empleado\n" + 
									"WHERE movimiento.estatus NOT like 'Rack de prendas' AND movimiento.estatus NOT LIKE 'Rack de prendas registrado'" +
									
									
									"UNION\n" + 

									"SELECT movimientoRack.id_movimiento,\n" + 
									"movimientoRack.id_text,  \n" + 
									"cliente.nombre, \n" + 
									"cliente.apellido_paterno,  \n" + 
									"cliente.apellido_materno,  \n" + 
									"empleado.nombre_persona,  \n" + 
									"empleado.apellido_paterno as paterno,  \n" + 
									"empleado.apellido_materno as materno,  \n" + 
									"movimientoRack.fecha_salida,  \n" + 
									"movimientoRack.fecha_entrega,  \n" + 
									"movimientoRack.estatus,\n" + 
									"(SELECT SUM(precio.precio_muestrario*rack.cantidad)  \n" + 
									"		FROM alt_comercial_rack_prenda as rack\n" + 
									"		INNER JOIN alt_disenio_prenda prenda ON rack.id_prenda = prenda.id_prenda\n" + 
									"		INNER JOIN alt_disenio_tela tela ON rack.id_tela = tela.id_tela\n" + 
									"		INNER JOIN alt_disenio_lista_precio_prenda precio ON rack.id_prenda = precio.id_prenda\n" + 
									"														AND tela.id_familia_composicion = precio.id_familia_composicion \n" + 
									"		WHERE rack.id_movimiento = movimientoRack.id_movimiento) AS Total,\n" + 
									"movimientoRack.encargado\n" + 
									"\n" + 
									"FROM alt_comercial_movimiento as movimientoRack\n" + 
									"INNER JOIN alt_comercial_cliente cliente ON movimientoRack.empresa = id_cliente \n" + 
									"INNER JOIN alt_hr_empleado empleado ON movimientoRack.vendedor = empleado.id_empleado  \n" + 
									"\n" + 
									"WHERE movimientoRack.estatus = 'Rack de prendas' OR movimientoRack.estatus = 'Rack de prendas registrado' "
									+ "ORDER BY id_movimiento DESC").getResultList();
	}
	
	@Override
	@Transactional
	public List<Object> findAllWithNamesByAgente(Long idVendedor){
		return em.createNativeQuery("SELECT movimiento.id_movimiento, \n" + 
											"movimiento.id_text, \n" + 
											"cliente.nombre,\n" + 
											"cliente.apellido_paterno, \n" + 
											"cliente.apellido_materno, \n" + 
											"empleado.nombre_persona, \n" + 
											"empleado.apellido_paterno as paterno, \n" + 
											"empleado.apellido_materno as materno, \n" + 
											"movimiento.fecha_salida, \n" + 
											"movimiento.fecha_entrega, \n" + 
											"movimiento.estatus, \n" + 
											"(SELECT SUM(precio.precio_muestrario) \n" + 
											" FROM alt_comercial_movimiento_muestra_detalle as muest\n" + 
											"	INNER JOIN alt_disenio_prenda prenda ON muest.modelo_prenda = prenda.id_prenda\n" +
											"   INNER JOIN alt_disenio_tela tela ON muest.codigo_tela = tela.id_tela\n" +
											"	INNER JOIN alt_disenio_lista_precio_prenda precio ON muest.modelo_prenda = precio.id_prenda " +
											 													"AND tela.id_familia_composicion = precio.id_familia_composicion\n" + 
											"	WHERE muest.id_movimiento = movimiento.id_movimiento\n" +
											"   AND (muest.estatus=1 OR muest.estatus=2 OR muest.estatus=6 OR muest.estatus=7)) AS Total, movimiento.encargado\n" + 
									"from alt_comercial_movimiento as movimiento\n" + 
									"	INNER JOIN alt_comercial_cliente cliente ON movimiento.empresa = id_cliente\n" + 
									"	INNER JOIN alt_hr_empleado empleado ON movimiento.vendedor = empleado.id_empleado\n" + 
									"WHERE movimiento.estatus NOT like 'Rack de prendas' AND movimiento.estatus NOT LIKE 'Rack de prendas registrado'"
									+ " AND movimiento.vendedor = "+idVendedor +
									
									" UNION\n" + 

									"SELECT movimientoRack.id_movimiento,\n" + 
									"movimientoRack.id_text,  \n" + 
									"cliente.nombre, \n" + 
									"cliente.apellido_paterno,  \n" + 
									"cliente.apellido_materno,  \n" + 
									"empleado.nombre_persona,  \n" + 
									"empleado.apellido_paterno as paterno,  \n" + 
									"empleado.apellido_materno as materno,  \n" + 
									"movimientoRack.fecha_salida,  \n" + 
									"movimientoRack.fecha_entrega,  \n" + 
									"movimientoRack.estatus,\n" + 
									"(SELECT SUM(precio.precio_muestrario*rack.cantidad)  \n" + 
									"		FROM alt_comercial_rack_prenda as rack\n" + 
									"		INNER JOIN alt_disenio_prenda prenda ON rack.id_prenda = prenda.id_prenda\n" + 
									"		INNER JOIN alt_disenio_tela tela ON rack.id_tela = tela.id_tela\n" + 
									"		INNER JOIN alt_disenio_lista_precio_prenda precio ON rack.id_prenda = precio.id_prenda\n" + 
									"														AND tela.id_familia_composicion = precio.id_familia_composicion \n" + 
									"		WHERE rack.id_movimiento = movimientoRack.id_movimiento) AS Total,\n" + 
									"movimientoRack.encargado\n" + 
									"\n" + 
									"FROM alt_comercial_movimiento as movimientoRack\n" + 
									"INNER JOIN alt_comercial_cliente cliente ON movimientoRack.empresa = id_cliente \n" + 
									"INNER JOIN alt_hr_empleado empleado ON movimientoRack.vendedor = empleado.id_empleado  \n" + 
									"\n" + 
									"WHERE (movimientoRack.estatus = 'Rack de prendas' OR movimientoRack.estatus = 'Rack de prendas registrado')"
									+ " AND movimientoRack.vendedor = "+idVendedor+" ORDER BY id_movimiento DESC").getResultList();
	}
	
	@Override
	@Transactional
	public List<Object> listarMuestrasTraspaso(Long id) {
		return em.createNativeQuery("select prenda.descripcion_prenda, movimiento.codigo_barras, telas.nombre_tela, movimiento.id_detalle_pedido, prenda.id_text AS text, telas.id_text from alt_comercial_movimiento_muestra_detalle movimiento\r\n" + 
									"		INNER JOIN alt_disenio_prenda prenda ON movimiento.modelo_prenda = prenda.id_prenda\r\n" + 
									"		INNER JOIN alt_disenio_tela telas ON movimiento.codigo_tela = telas.id_tela\r\n" + 
									"		\r\n" + 
									"WHERE movimiento.id_movimiento = "+id+"\r\n" + 
									"AND (movimiento.estatus = 4 or movimiento.estatus = 6 or movimiento.estatus = 8)\r\n" + 
									"GROUP BY movimiento.id_detalle_pedido").getResultList();
	}
	
	@Override
	@Transactional
	public List<Object> datosMovimiento(Long id) {

		return em.createNativeQuery("SELECT movimiento.empresa, " +
										   "movimiento.vendedor, " +
										   "prenda.descripcion_prenda, " +
										   "movidetalle.codigo_barras, " +
										   "movidetalle.id_detalle_pedido, " +
										   "prenda.id_text AS text, " +
										   "telas.id_text, " +
										   "prenda.id_prenda, " +
										   "telas.id_tela, " +
										   "movidetalle.id_movimiento, " +
										   "movimiento.encargado " +
									"FROM alt_comercial_movimiento AS movimiento\r\n" + 
									"\r\n" + 
									"INNER JOIN alt_comercial_movimiento_muestra_detalle movidetalle ON movimiento.id_movimiento = movidetalle.id_movimiento\r\n" + 
									"INNER JOIN alt_disenio_prenda prenda ON movidetalle.modelo_prenda = prenda.id_prenda\r\n" + 
									"INNER JOIN alt_disenio_tela telas ON movidetalle.codigo_tela = telas.id_tela\r\n" + 
									"WHERE movimiento.id_movimiento = "+id+"\r\n" + 
									"GROUP BY movidetalle.id_detalle_pedido").getResultList();
	}

	@Override
	public List<Object> findAllHistorico() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_historico_muestras()").getResultList();
	}
	
	
	@Override
	@Transactional
	public List<Object []> findAllExpirados() {

		return em.createNativeQuery(""
				+ "SELECT\n" + 
				"	movi_deta.codigo_barras,\n" + 
				"	prenda.descripcion_prenda,\n" + 
				"	look.nombre_lookup,\n" + 
				"	pedido.talla,\n" + 
				"	pedido.largo,\n" + 
				"	tela.id_tela,\n" + 
				"	DATE_FORMAT(movi_deta.fecha_salida, '%d-%m-%Y %H:%i:%s' ),\r\n" + 
			
				"	cliente.nombre,\n" + 
				"	FLOOR( DATEDIFF( CURDATE(), movi_deta.fecha_salida ) / 7 ) * 5 + least( DATEDIFF( CURDATE(), movi_deta.fecha_salida ) % 7, 5 ) +\n" + 
				"IF\n" + 
				"	( weekday( CURDATE()) < weekday( movi_deta.fecha_salida ), - 2, 0 ) AS diasHabiles \n" + 
				"FROM\n" + 
				"	alt_comercial_movimiento AS movi,\n" + 
				"	alt_comercial_movimiento_muestra_detalle AS movi_deta,\n" + 
				"	alt_produccion_detalle_pedido AS pedido,\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_comercial_cliente AS cliente,\n" + 
				"	alt_disenio_lookup AS look,\n" + 
				"	alt_disenio_tela AS tela,\n" + 
				"	alt_disenio_lookup AS lookG \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND movi.id_movimiento = movi_deta.id_movimiento \n" + 
				"	AND movi_deta.id_detalle_pedido = pedido.id_detalle_pedido \n" + 
				"	AND prenda.id_prenda = pedido.id_prenda \n" + 
				"	AND pedido.estatus = 2 \n" + 
				"	AND cliente.id_cliente = movi.empresa \n" + 
				"	AND movi_deta.estatus = 4 \n" + 
				"	AND look.id_lookup = prenda.id_familia_prenda \n" + 
				"	AND tela.id_tela = pedido.id_tela \n" + 
				"GROUP BY\n" + 
				"	movi_deta.id_detalle_pedido").getResultList();
	}

}
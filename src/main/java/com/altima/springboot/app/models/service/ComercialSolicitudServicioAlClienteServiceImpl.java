package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlCliente;
import com.altima.springboot.app.repository.ComercialSolicitudServicioAlClienteRepository;

@Service
public class ComercialSolicitudServicioAlClienteServiceImpl implements IComercialSolicitudServicioAlClienteService{
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialSolicitudServicioAlClienteRepository repository;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAll(Long id) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT \r\n" + 
				"	solicitud.id_solicitud_servicio_al_cliente,\r\n" + 
				"   solicitud.id_text AS idText1,\r\n" + 
				"  	DATE_FORMAT(solicitud.fecha_creacion,'%Y-%m-%d'),\r\n" + 
				"   agente.nombre_usuario,\r\n" + 
				"	if ( solicitud.id_pedido_informacion is null, 'N/A', pedido.id_text) AS IdText2,\r\n" + 
				"   cliente.nombre,\r\n" + 
				"   DATE_FORMAT(solicitud.fecha_hora_de_cita,'%Y-%m-%d %k:%i'),\r\n" + 
				"   solicitud.actividad,\r\n" + 
				"   solicitud.estatus\r\n" + 
				"FROM \r\n" + 
				"alt_comercial_pedido_informacion as pedido ,\r\n" + 
				"alt_comercial_solicitud_servicio_al_cliente solicitud \r\n" + 
				"INNER JOIN alt_comercial_cliente AS cliente ON solicitud.id_cliente = cliente.id_cliente\r\n" + 
				"INNER JOIN alt_hr_usuario AS agente ON cliente.id_usuario = agente.id_usuario\r\n" + 
				"WHERE IF("+id+"=0,1=1,agente.id_usuario="+id+")\r\n"+
				"AND  (solicitud.id_pedido_informacion = pedido.id_pedido_informacion or solicitud.id_pedido_informacion is null)\r\n" + 
				"GROUP BY solicitud.id_solicitud_servicio_al_cliente\r\n" + 
				"ORDER BY solicitud.id_solicitud_servicio_al_cliente desc\r\n" + 
				"").getResultList();
		
		
		return re;
	}

	@Override
	public void save(ComercialSolicitudServicioAlCliente comercialSolicitudServicioAlCliente) {
		// TODO Auto-generated method stub
		repository.save(comercialSolicitudServicioAlCliente);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public ComercialSolicitudServicioAlCliente findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> pedidosDeCliente(Long id) {
		List<Object[]> re = em.createNativeQuery(
				"SELECT "
				+ "informacion.id_pedido_informacion, "
				+ "informacion.id_text  "
				+ "from alt_comercial_pedido_informacion as informacion \r\n"
				+ "where informacion.id_empresa=" + id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> devolverSelectMateriales(Long idSolicitud) {
	
		List<Object[]> response = em.createNativeQuery("SELECT \r\n" + 
				"	material.id_lookup AS ID,\r\n" + 
				"    material.nombre_lookup AS NOM\r\n" + 
				"    FROM alt_servicio_cliente_lookup AS material\r\n" + 
				"    WHERE material.tipo_lookup = \"Material\"\r\n" + 
				"    AND material.id_lookup NOT IN(\r\n" + 
				"		SELECT \r\n" + 
				"			material2.id_lookup AS ID2\r\n" + 
				"			FROM alt_servicio_cliente_lookup AS material2\r\n" + 
				"			INNER JOIN alt_comercial_solicitud_servicio_al_cliente_material AS solicitud ON material2.id_lookup = solicitud.id_lookup\r\n" + 
				"			WHERE material2.tipo_lookup = \"Material\"\r\n" + 
				"			AND solicitud.id_solicitud_servicio_al_cliente = " + idSolicitud + ");").getResultList();
		return response;
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> direccionesSucursales(Long id) {
		// TODO Auto-generated method stub
		List<Object[]> response = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	'MATRIZ',\r\n" + 
				"	CONCAT( 'PPAL. ', cliente.nombre ),\r\n" + 
				"	cliente.telefono,\r\n" + 
				"	cliente.nombre_contacto,\r\n" + 
				"	CONCAT(\r\n" + 
				"		direccion.calle,\r\n" + 
				"		', #',\r\n" + 
				"		direccion.numero_ext,\r\n" + 
				"		'. Colonia: ',\r\n" + 
				"		direccion.colonia,\r\n" + 
				"		'. ',\r\n" + 
				"		direccion.municipio,\r\n" + 
				"		', ',\r\n" + 
				"		direccion.estado,\r\n" + 
				"		'. CP:',\r\n" + 
				"		direccion.codigo_postal \r\n" + 
				"	) \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_cliente AS cliente,\r\n" + 
				"	alt_hr_direccion AS direccion \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND cliente.id_cliente = "+id+" \r\n" + 
				"	AND cliente.id_direccion = direccion.id_direccion UNION ALL\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		sucursal.id_cliente_sucursal,\r\n" + 
				"		CONCAT( 'Suc. ', sucursal.nombre_sucursal ),\r\n" + 
				"		sucursal.telefono_sucursal,\r\n" + 
				"		sucursal.contacto_sucursal,\r\n" + 
				"		CONCAT(\r\n" + 
				"			direccion.calle,\r\n" + 
				"			', #',\r\n" + 
				"			direccion.numero_ext,\r\n" + 
				"			'. Colonia: ',\r\n" + 
				"			direccion.colonia,\r\n" + 
				"			'. ',\r\n" + 
				"			direccion.municipio,\r\n" + 
				"			', ',\r\n" + 
				"			direccion.estado,\r\n" + 
				"			'. CP:',\r\n" + 
				"			direccion.codigo_postal \r\n" + 
				"		) \r\n" + 
				"	FROM\r\n" + 
				"		alt_comercial_cliente AS cliente,\r\n" + 
				"		alt_comercial_cliente_sucursal AS sucursal,\r\n" + 
				"		alt_hr_direccion AS direccion \r\n" + 
				"	WHERE\r\n" + 
				"		1 = 1 \r\n" + 
				"		AND cliente.id_cliente = sucursal.id_cliente \r\n" + 
				"		AND sucursal.id_direccion = direccion.id_direccion \r\n" + 
				"		AND sucursal.estatus = 1 \r\n" + 
				"		AND cliente.id_cliente = "+id+" \r\n" + 
				"ORDER BY\r\n" + 
				"	sucursal.nombre_sucursal)").getResultList();
		return response;
	}
}

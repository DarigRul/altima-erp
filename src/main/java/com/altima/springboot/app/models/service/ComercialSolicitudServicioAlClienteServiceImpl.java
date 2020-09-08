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
	public List<Object[]> findAll() {
		// TODO Auto-generated method stub
		List<Object[]> re = em.createNativeQuery("SELECT \r\n" + 
				"	solicitud.id_solicitud_servicio_al_cliente,\r\n" + 
				"    solicitud.id_text AS idText1,\r\n" + 
				"    solicitud.fecha_creacion,\r\n" + 
				"    agente.nombre_usuario,\r\n" + 
				"    pedido.id_text AS IdText2,\r\n" + 
				"    cliente.nombre,\r\n" + 
				"    solicitud.fecha_hora_de_cita,\r\n" + 
				"    solicitud.actividad,\r\n" + 
				"    solicitud.estatus\r\n" + 
				"    \r\n" + 
				"FROM alt_comercial_solicitud_servicio_al_cliente solicitud\r\n" + 
				"\r\n" + 
				"INNER JOIN alt_comercial_pedido_informacion AS pedido ON solicitud.id_pedido_informacion = pedido.id_pedido_informacion\r\n" + 
				"INNER JOIN alt_comercial_cliente AS cliente ON solicitud.id_cliente = cliente.id_cliente\r\n" + 
				"INNER JOIN alt_hr_usuario AS agente ON pedido.id_usuario = agente.id_usuario;").getResultList();
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
		// TODO Auto-generated method stub
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
}

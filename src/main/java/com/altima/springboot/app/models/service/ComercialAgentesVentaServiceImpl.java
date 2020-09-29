package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialAgentesVenta;
import com.altima.springboot.app.repository.ComercialAgentesVentaRepository;

@SuppressWarnings("unchecked")
@Service
public class ComercialAgentesVentaServiceImpl implements IComercialAgentesVentaService{

	@Autowired
	private ComercialAgentesVentaRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional
	public void save(ComercialAgentesVenta agentesVenta) {
		// TODO Auto-generated method stub
		repository.save(agentesVenta);
		
	}

	@Override
	@Transactional
	public ComercialAgentesVenta findOne(Long idEmpleado) {
		// TODO Auto-generated method stub
		return (ComercialAgentesVenta) em.createQuery("FROM ComercialAgentesVenta WHERE idEmpleado="+idEmpleado).getSingleResult();
	}

	@Override
	public List<Object[]> findAllByNombreEmpleado() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT empleado.id_empleado,\r\n" + 
									"		empleado.id_text,\r\n" + 
									"		empleado.nombre_persona, \r\n" + 
									"		empleado.apellido_materno, \r\n" + 
									"		empleado.apellido_paterno, \r\n" + 
									"		agenteVentas.foraneo, \r\n" + 
									"		agenteVentas.licitacion,\r\n" + 
									"		agenteVentas.creado_por,\r\n" + 
									"		agenteVentas.actualizado_por,\r\n" + 
									"		agenteVentas.fecha_creacion,\r\n" + 
									"		agenteVentas.ultima_fecha_modificacion,\r\n" + 
									"		agenteVentas.estatus\r\n" +
									"FROM alt_hr_empleado AS empleado\r\n" + 
									"INNER JOIN alt_comercial_agentes_venta agenteVentas ON empleado.id_empleado = agenteVentas.id_empleado").getResultList();
	}
	
	@Override
	@Transactional
	public String finduplicated(Long idEmpleado) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT IF((SELECT COUNT(*) FROM alt_comercial_agentes_venta WHERE id_empleado="+idEmpleado+")>0, 1 , 0)").getSingleResult().toString();
	}

}

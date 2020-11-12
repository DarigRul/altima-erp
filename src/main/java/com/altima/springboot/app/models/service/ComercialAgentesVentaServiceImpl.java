package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.GroupSequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialAgentesVenta;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
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
		return repository.findById(idEmpleado).orElse(null);
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
	
	@Override
	@Transactional
	public List<Object[]> findAllApartadoTelas() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT Pedido.id_pedido_informacion, \r\n" + 
									"		Pedido.id_text,\r\n" + 
									"		CONCAT(cliente.nombre,' ',IFNULL('',cliente.apellido_paterno),' ',IFNULL('',cliente.apellido_materno)) AS Empresa, \r\n" + 
									"		Pedido.tipo_pedido, \r\n" + 
									"		Pedido.fecha_toma_tallas, \r\n" + 
									"		Pedido.fecha_entrega, \r\n" + 
									"		Pedido.fecha_anticipo, \r\n" + 
									"		Pedido.fecha_cierre, \r\n" + 
									"		Pedido.fecha_creacion, \r\n" + 
									"		Pedido.ultima_fecha_creacion, \r\n" + 
									"		Pedido.estatus, \r\n" + 
									"		Pedido.fecha_apartado_telas \r\n" +
									"FROM alt_comercial_pedido_informacion AS Pedido\r\n" + 
									"INNER JOIN alt_comercial_cliente cliente ON Pedido.id_empresa = cliente.id_cliente\r\n" + 
									"INNER JOIN alt_hr_usuario usuario ON Pedido.id_usuario = usuario.id_usuario\r\n" + 
									"WHERE Pedido.estatus = 3 ORDER BY Pedido.id_text DESC").getResultList();
	}

	@Override
	@Transactional
	public List<Object[]> findDatosReporteApartadoTelas (Long id,boolean agrupar){
		return em.createNativeQuery("Call alt_pr_apartado_telas_reporte("+id+","+agrupar+")").getResultList();
	}
}


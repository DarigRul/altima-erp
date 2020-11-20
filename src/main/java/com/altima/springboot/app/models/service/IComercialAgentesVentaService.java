package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialAgentesVenta;


public interface IComercialAgentesVentaService {

	void save (ComercialAgentesVenta agentesVenta);

	ComercialAgentesVenta findOne (Long idEMpleado);
	
	List<Object[]> findAllByNombreEmpleado ();

	String finduplicated(Long idEmpleado);

	List<Object[]> findAllApartadoTelas();

	List<Object[]> findDatosReporteApartadoTelas(Long id,boolean agrupar);
}

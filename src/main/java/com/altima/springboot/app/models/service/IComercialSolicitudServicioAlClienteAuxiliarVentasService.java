package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteAuxiliarVentas;

public interface IComercialSolicitudServicioAlClienteAuxiliarVentasService {
	
	void save(ComercialSolicitudServicioAlClienteAuxiliarVentas ComercialSolicitudServicioAlClienteAuxiliarVentas);
	
	void delete(Long id);
	
	List<ComercialSolicitudServicioAlClienteAuxiliarVentas> findBySolicitud(Long id);
	
	List<String> devolverSelectAuxiliarVentas(Long idSolicitud);
}

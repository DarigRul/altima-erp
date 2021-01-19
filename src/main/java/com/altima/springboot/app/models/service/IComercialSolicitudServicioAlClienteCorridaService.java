package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteCorrida;

public interface IComercialSolicitudServicioAlClienteCorridaService {
	
	void save(ComercialSolicitudServicioAlClienteCorrida ComercialSolicitudServicioAlClienteCorrida);
	
	void delete(Long id);
	
	List<ComercialSolicitudServicioAlClienteCorrida> findBySolicitud(Long id);
	
	List<String> devolverSelectCorridas(Long idSolicitud);

	List<String> devolverSelectCorridasTipo(Long idSolicitud, String genero);
}

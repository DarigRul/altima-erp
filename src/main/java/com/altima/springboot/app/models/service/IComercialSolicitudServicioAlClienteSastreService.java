package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteSastre;

public interface IComercialSolicitudServicioAlClienteSastreService {
	
	void save(ComercialSolicitudServicioAlClienteSastre ComercialSolicitudServicioAlClienteSastre);
	
	void delete(Long id);
	
	List<ComercialSolicitudServicioAlClienteSastre> findBySolicitud(Long idSolicitud);
	
	List<String> devolverSelectSastre(Long idSolicitud);
}

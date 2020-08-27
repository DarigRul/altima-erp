package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteMaterial;

public interface IComercialSolicitudServicioAlClienteMaterialService {
	
	void save(ComercialSolicitudServicioAlClienteMaterial ComercialSolicitudServicioAlClienteMaterial);
	
	void delete(Long id);
	
	List<ComercialSolicitudServicioAlClienteMaterial> findBySolicitud(Long id);
}

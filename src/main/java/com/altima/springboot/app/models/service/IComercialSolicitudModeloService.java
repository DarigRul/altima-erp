package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ComercialSolicitudModeloDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudModelo;

public interface IComercialSolicitudModeloService {
    void save(ComercialSolicitudModelo solicitudModelo);
	
	void delete(Long id);
	
	List<ComercialSolicitudModelo> findAll();
	
	ComercialSolicitudModelo findOne(Long idSolicitud);

	List<ComercialSolicitudModeloDTO> findAllSolicitud(Long idAgente);
}

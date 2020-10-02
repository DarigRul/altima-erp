package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ComercialSolicitudModeloDetalleDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudModeloDetalle;

public interface IComercialSolicitudModeloDetalleService {

    void save(ComercialSolicitudModeloDetalle solicitudModeloDetalle);
	
	void delete(Long id);
	
	List<ComercialSolicitudModeloDetalle> findAll();
	
    ComercialSolicitudModeloDetalle findOne(Long id);
    
    List<ComercialSolicitudModeloDetalleDTO> findAllByidSolicitud(Long id);
}

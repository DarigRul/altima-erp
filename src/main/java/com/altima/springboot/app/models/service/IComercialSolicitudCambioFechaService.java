package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ComercialSolicitudCambioFechaDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudCambioFecha;

public interface IComercialSolicitudCambioFechaService {

    void save(ComercialSolicitudCambioFecha solicitud);
	
	void delete(Long id);
	
	List<ComercialSolicitudCambioFecha> findAll();
	
    ComercialSolicitudCambioFecha findOne(Long id);    

    List<ComercialSolicitudCambioFechaDTO> findAllDetalle();
    
}

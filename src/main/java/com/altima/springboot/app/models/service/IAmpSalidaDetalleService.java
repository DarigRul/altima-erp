package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpSalidaDetalle;

public interface IAmpSalidaDetalleService {
    
    List<AmpSalidaDetalle> findAll();

	void save(AmpSalidaDetalle salida);

	void delete(Long id);

	AmpSalidaDetalle findOne(Long id);
}

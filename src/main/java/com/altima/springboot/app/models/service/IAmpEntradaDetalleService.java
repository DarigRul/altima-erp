package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpEntradaDetalle;

public interface IAmpEntradaDetalleService {
        
    List<AmpEntradaDetalle> findAll();

	void save(AmpEntradaDetalle entradaDetalle);

	void delete(Long id);

	AmpEntradaDetalle findOne(Long id);
}

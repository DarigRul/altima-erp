package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;

public interface IAmpTraspasoDetalleService {
    
    List<AmpTraspasoDetalle> findAll();

	void save(AmpTraspasoDetalle traspasoDetalle);

	void delete(Long id);

	AmpTraspasoDetalle findOne(Long id);
}

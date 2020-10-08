package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpSalida;

public interface IAmpSalidaService {
    
    List<AmpSalida> findAll();

	void save(AmpSalida salida);

	void delete(Long id);

	AmpSalida findOne(Long id);
}

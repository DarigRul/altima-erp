package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpEntrada;

public interface IAmpEntradaService {
    
    List<AmpEntrada> findAll();

	void save(AmpEntrada entrada);

	void delete(Long id);

	AmpEntrada findOne(Long id);
}

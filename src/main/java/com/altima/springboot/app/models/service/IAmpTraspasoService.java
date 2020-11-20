package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpTraspaso;

public interface IAmpTraspasoService {
    
    List<AmpTraspaso> findAll();

	void save(AmpTraspaso traspaso);

	void delete(Long id);

	AmpTraspaso findOne(Long id);
}

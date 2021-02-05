package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionMaquiladorPrendas;

public interface IProduccionMaquiladorPrendasService {
    
    List<ProduccionMaquiladorPrendas> findAll();

	void save(ProduccionMaquiladorPrendas maquiladorPrendas);

	void delete(Long idMaquilador,Long idFamiliaPrenda);

	ProduccionMaquiladorPrendas findOne(Long id);
}

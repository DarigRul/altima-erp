package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionMaquilador;

public interface IProduccionMaquiladorService {
    
    List<ProduccionMaquilador> findAll();

    void save(ProduccionMaquilador maquilador);

	void delete(Long id);

    ProduccionMaquilador findOne(Long id);


    List<Object[]> findAllCompleto();
    
}

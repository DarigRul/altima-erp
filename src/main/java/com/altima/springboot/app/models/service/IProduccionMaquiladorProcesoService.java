package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionMaquiladorProceso;

public interface IProduccionMaquiladorProcesoService {

    List<ProduccionMaquiladorProceso> findAll();

    void save(ProduccionMaquiladorProceso maquiladorProceso);

	void delete(Long idProceso,Long idMaquilador);

    ProduccionMaquiladorProceso findOne(Long id);
    
}

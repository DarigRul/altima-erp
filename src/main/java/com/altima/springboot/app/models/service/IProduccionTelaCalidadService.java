package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionTelaCalidad;

public interface IProduccionTelaCalidadService {
    
    void save(ProduccionTelaCalidad telaCalidad);

    List<ProduccionTelaCalidad> findAll();

    ProduccionTelaCalidad findOne(Long idTelaCalidad);

    ProduccionTelaCalidad findOneByIdTela(Long idTela);

    void delete(Long idTelaCalidad);
}

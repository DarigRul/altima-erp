package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionTelaCalidadImagen;

public interface IProduccionTelaCalidadImagenService {

    void save(ProduccionTelaCalidadImagen telaCalidad);

    List<ProduccionTelaCalidadImagen> findAll();

    ProduccionTelaCalidadImagen findOne(Long idTelaCalidad);

    void delete(Long idTelaCalidad);

    List<ProduccionTelaCalidadImagen> findByIdTela(Long idTela);
    
}

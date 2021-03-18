package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionMiniTrazo;

public interface IProduccionMiniTrazoService {

    List<ProduccionMiniTrazo> findAll();

    void save(ProduccionMiniTrazo miniTrazo);

    void delete(Long id);

    ProduccionMiniTrazo findOne(Long id);

    List<ProduccionMiniTrazo> findByIdPrenda(Long idPrenda);
}

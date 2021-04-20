package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPrendasTiemposCondicion;

public interface IProduccionPrendasTiemposCondicionService {

    List<ProduccionPrendasTiemposCondicion> findAll();

    void save(ProduccionPrendasTiemposCondicion tiempos);

    void delete(Long id);

    ProduccionPrendasTiemposCondicion findOne(Long id);
}

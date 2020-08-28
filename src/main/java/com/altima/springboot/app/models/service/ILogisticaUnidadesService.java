package com.altima.springboot.app.models.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.models.entity.LogisticaUnidad;

@Service
public interface ILogisticaUnidadesService {

    List<LogisticaUnidad> findAll();
    List<HrPuesto> findAllPosition();
    void save(LogisticaUnidad unidad);
    LogisticaUnidad findOne(Long id);
    boolean findOneByPlaca(String placa);
    boolean findOneById(Long id);

}
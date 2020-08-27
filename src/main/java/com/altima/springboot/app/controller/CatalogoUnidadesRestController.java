package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.LogisticaUnidad;
import com.altima.springboot.app.models.service.ILogisticaUnidadesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogoUnidadesRestController {

    @Autowired
    ILogisticaUnidadesService unidadService;

    @GetMapping("/logistica-catalogos-unidades-datos")
    public LogisticaUnidad EditUnidad(@RequestParam(name = "idJS") Long id) {
        return unidadService.findOne(id);
    }
}
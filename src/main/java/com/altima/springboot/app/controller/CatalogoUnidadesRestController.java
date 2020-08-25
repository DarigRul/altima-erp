package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.LogisticaUnidad;
import com.altima.springboot.app.models.service.ILogisticaUnidadesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogoUnidadesRestController {

    @Autowired
    ILogisticaUnidadesService unidadService;

    @PostMapping("/logistica-catalogos-unidades-editar")
    public int AddUnidad(@RequestParam(name = "idJS") Long id, @RequestParam(name = "no_placaJS")
    String placa, @RequestParam(name = "choferJS") Long chofer, @RequestParam(name = "modeloJS") String modelo) {
        LogisticaUnidad unidad = new LogisticaUnidad();
        try {
            unidadService.findOne(id);
            unidad.setIdText(placa);
            unidad.setIdEmpleado(chofer);
            unidad.setModelo(modelo);
            unidadService.save(unidad);
		    return 1;

        } catch (Exception e) {
            return 2;
        }
    }
    @GetMapping("/logistica-catalogos-unidades-datos")
    public LogisticaUnidad EditUnidad(@RequestParam(name = "idJS") Long id) {
        return unidadService.findOne(id);
    }
}
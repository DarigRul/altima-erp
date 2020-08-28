package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.altima.springboot.app.models.entity.LogisticaUnidad;
import com.altima.springboot.app.models.service.ILogisticaUnidadesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogoUnidadesRestController {

    @Autowired
    ILogisticaUnidadesService unidadService;

    @PostMapping("/unidad-nueva")
    public int addUnity(@RequestParam(name = "placaJS") String placa,
    @RequestParam(name = "modeloJS") String modelo,@RequestParam(name = "choferJS") Long chofer, @RequestParam(name = "idJS") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            LogisticaUnidad unidad = unidadService.findOne(id);
            unidad.setIdText(placa);
            unidad.setModelo(modelo);
            unidad.setIdEmpleado(chofer);
            unidadService.save(unidad);
            return 1;
        } catch (Exception e) {
            try {
                LogisticaUnidad unidad = new LogisticaUnidad();
                unidad.setIdText(placa);
                unidad.setModelo(modelo);
                unidad.setIdEmpleado(chofer);
                unidad.setCreadoPor(auth.getName());
                unidad.setFechaCreacion(dtf.format(now));
                unidad.setEstatus("1");
                unidadService.save(unidad);
                return 2;
            } catch (Exception p) {
                p.printStackTrace();
                return 3;
            }
        } finally {
        }
    }

    @GetMapping("/logistica-catalogos-unidades-datos")
    public LogisticaUnidad EditUnidad(@RequestParam(name = "idJS") Long id) {
        return unidadService.findOne(id);
    }
    @GetMapping("/duplicado-unidad")
    public boolean duplicadoUnidad(@RequestParam(name = "placaJS") String unidadDuplicado) {
        boolean d;
        try {
            d = unidadService.findOneByPlaca(unidadDuplicado);
        } catch (Exception e) {
            d = unidadService.findOneByPlaca(unidadDuplicado);
        }
        return d;
    }
    @GetMapping("/duplicado-id-unidad")
    public boolean duplicadoUnidadById(@RequestParam(name = "idJS") Long id) {
        boolean d;
        try {
            d = unidadService.findOneById(id);
        } catch (Exception e) {
            d = unidadService.findOneById(id);
        }
        return d;
    }
    // Método para dar de baja empresa
    @GetMapping("/baja-unidad")
    public Object BajaUnidad(@RequestParam(name = "idJS") Long id) throws Exception {
        LogisticaUnidad unidad = unidadService.findOne(id);
        unidad.setEstatus("0");
        unidadService.save(unidad);
        return unidadService.findOne(id);
    }

    // Método para dar de alta empresa
    @GetMapping("/alta-unidad")
    public Object AltaUnidad(@RequestParam(name = "idJS") Long id) throws Exception {
        LogisticaUnidad unidad = unidadService.findOne(id);
        unidad.setEstatus("1");
        unidadService.save(unidad);
        return unidadService.findOne(id);
    }
}
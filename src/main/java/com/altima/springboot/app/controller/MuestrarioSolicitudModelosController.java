package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IComercialSolicitudModeloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MuestrarioSolicitudModelosController {

    @Autowired
    IComercialSolicitudModeloService solicitudModeloService;

    @GetMapping("solicitud-de-modelos")
    public String index(Model m){
        m.addAttribute("solicitudes", solicitudModeloService.findAllSolicitud());
        return "solicitud-de-modelos";
    }
}

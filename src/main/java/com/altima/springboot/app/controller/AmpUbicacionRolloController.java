package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IAmpAlmacenUbicacionArticuloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AmpUbicacionRolloController {

    @Autowired
    IAmpAlmacenUbicacionArticuloService almacenUbicacionArticuloService;

    @GetMapping("/amp-ubicaciones-rollos")
    public String ampUbicacionesRollos(Model m) {
        m.addAttribute("casilleros", almacenUbicacionArticuloService.findAllRollo());
        return "amp-ubicaciones-rollos";
    }
    
}

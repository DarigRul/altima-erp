package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IAmpTelaFaltanteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComprasRequisicionTelasController {

    @Autowired
    IAmpTelaFaltanteService telaFaltanteService;

    @GetMapping("/requisicion-de-telas")
    public String RequisicionTelasList(Model m){
        m.addAttribute("telasFaltantes", telaFaltanteService.findAllTelasFaltantes());
        return"requisicion-de-telas";
    }
}
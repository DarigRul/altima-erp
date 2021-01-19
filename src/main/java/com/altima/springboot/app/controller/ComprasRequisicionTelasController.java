package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.AmpTelaFaltante;
import com.altima.springboot.app.models.service.IAmpTelaFaltanteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ComprasRequisicionTelasController {

    @Autowired
    IAmpTelaFaltanteService telaFaltanteService;

    @GetMapping("/requisicion-de-telas")
    public String RequisicionTelasList(Model m){
        m.addAttribute("telasFaltantes", telaFaltanteService.findAllTelasFaltantes());
        return"requisicion-de-telas";
    }

    @GetMapping("/requisicion-de-telas/{id}/{estatusComercial}")
    public String EstatusComercial(Model m,@PathVariable Long id,@PathVariable Boolean estatusComercial){
        AmpTelaFaltante telaFaltante= telaFaltanteService.findOne(id);
        if (estatusComercial) {
            telaFaltante.setEstatusComercial(1);
        } else {
            telaFaltante.setEstatusComercial(2);
        }
        telaFaltanteService.save(telaFaltante);
        return"redirect:/requisicion-de-telas";
    }
}
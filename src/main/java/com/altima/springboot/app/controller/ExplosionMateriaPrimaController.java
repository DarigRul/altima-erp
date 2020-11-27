package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IAmpExplosionTelaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class ExplosionMateriaPrimaController {
    
    @Autowired
    IAmpExplosionTelaService explosionTelaService;
    
    @GetMapping("/materia-prima/{idPedido}")
    public String MateriaPrimaList(Model m,@PathVariable Long idPedido) {
        m.addAttribute("apartadoTelas", explosionTelaService.findAllExplosion(idPedido));
        return "materia-prima";
    }
}

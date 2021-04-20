package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IProduccionIncidenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IncidenciaController {
    @Autowired
    private IProduccionIncidenciaService incidenciaService;

    @GetMapping("/incidencia-produccion")
    public String view (Model model){
        model.addAttribute("maquileros", incidenciaService.maquileros());
        model.addAttribute("view", incidenciaService.view());
        return "incidencia-produccion";
    }
    
}

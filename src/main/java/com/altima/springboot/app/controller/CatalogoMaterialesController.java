package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IDisenioMaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogoMaterialesController {

    @Autowired
    private IDisenioMaterialService disenioMaterialService;

    @GetMapping("/catalogos-materiales-agentes")
    public String getMateriales(Model model){
        model.addAttribute("materiales", disenioMaterialService.disenioMaterialFiltro());
        return("catalogos-materiales-agentes");
    } 
    
}

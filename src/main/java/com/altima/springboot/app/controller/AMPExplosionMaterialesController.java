package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IAmpExplosionMaterialesService;

@Controller
public class AMPExplosionMaterialesController {
	@Autowired
	IAmpExplosionMaterialesService AmpExplosionMaterialesService;
	
    @GetMapping("/explosion-de-materiales")
    public String ExplosionList(Model model){
    	model.addAttribute("explosionmaterial", AmpExplosionMaterialesService.findAll());
        return"explosion-de-materiales";
    }
    @GetMapping("/explosion-de-material")
    public String MateriaPrimaList1(){
        return"explosion-de-material";
    }

    @GetMapping("/materia-prima")
    public String MateriaPrimaList(){
        return"materia-prima";
    }

    @GetMapping("/materiales-explosionar")
    public String MaterialesExplostionarList(){
        return"materiales-explosionar";
    }
}
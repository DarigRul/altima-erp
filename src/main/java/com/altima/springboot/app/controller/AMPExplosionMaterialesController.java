package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AMPExplosionMaterialesController {
    @GetMapping("/explosion-de-materiales")
    public String ExplosionList(){
        return"explosion-de-materiales";
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
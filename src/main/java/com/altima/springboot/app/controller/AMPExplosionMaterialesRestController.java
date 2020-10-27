package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.service.IAmpExplosionMaterialesService;

@RestController
public class AMPExplosionMaterialesRestController {
	@Autowired
	IAmpExplosionMaterialesService AmpExplosionMaterialesService;
	
    @GetMapping("/explosion-materiales-habilitacion")
    public List<Object[]> ExplosionMateriales(Model model,Long IdArticulo){
        return AmpExplosionMaterialesService.findAvailableMaterials(IdArticulo);
    }
    
}
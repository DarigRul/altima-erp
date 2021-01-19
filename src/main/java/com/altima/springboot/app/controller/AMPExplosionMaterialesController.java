package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.service.IAmpExplosionMaterialesService;

@Controller
public class AMPExplosionMaterialesController {
	@Autowired
	IAmpExplosionMaterialesService AmpExplosionMaterialesService;
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AMP_EXPLOSIONMATERIALES_LISTAR"})
    @GetMapping("/explosion-de-materiales")
    public String ExplosionList(Model model){
    	model.addAttribute("explosionmaterial", AmpExplosionMaterialesService.findAllExplosion());
        return"explosion-de-materiales";
    }
    @GetMapping("/explosion-de-material")
    public String MateriaPrimaList1(){
        return"explosion-de-material";
    }

    @Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AMP_EXPLOSIONMATERIALES_MATERIALES"})
    @GetMapping("/materiales-explosionar/{idpedido}")
    public String MaterialesExplostionarList(@PathVariable("idpedido") Long idpedido,Model model ){
    	model.addAttribute("materiales", AmpExplosionMaterialesService.findTotalMaterials(idpedido));
       model.addAttribute("idpedido", idpedido);
    	
    	return"materiales-explosionar";
    }
}

package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;
import com.altima.springboot.app.models.service.IProduccionLookupService;

@Controller
public class ProduccionControlAvancesController {

	@Autowired
	IProduccionLookupService lookupService;
	
	@Autowired
	IProduccionExplosionProcesosService explosionService;
	
	@GetMapping("control-avances")
	public String controlAvances(Model model) {
		
		model.addAttribute("listProcesos", lookupService.findAllByType("proceso"));
		
		return "control-avances";
	}
	
	@GetMapping("/finalizarProceso/{idExplosion}")
	public String finalizarProceso (@PathVariable(value="idExplosion")Long idExplosion) {
		
		ProduccionExplosionProcesos explosion = explosionService.findOne(idExplosion);
		System.out.println("entra?");
		explosion.setEstatusProceso(2);
		
		explosionService.save(explosion);
		
		return "redirect:/control-avances";
	}
}

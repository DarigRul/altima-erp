package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioRutaService;

@Controller
public class RutasController {
	
	@Autowired
	IDisenioLookupService disenioLookup;
	
	@Autowired
	IDisenioRutaService disenioruta;
	
	@GetMapping("/rutas") 
	public String listRutas(Model m) {
		m.addAttribute("lookupsProcesos", disenioLookup.findByTipoLookup("Proceso"));
		return "rutas";
	}
}

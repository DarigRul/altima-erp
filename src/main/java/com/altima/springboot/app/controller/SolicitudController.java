package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SolicitudController {
	
	@GetMapping("/mensajeria")
	public String listForms() {
		return "mensajeria";
	}
	
	@GetMapping("/generar-solicitud")
	public String generateForms() {
		return "generar-solicitud";
	}
	
}

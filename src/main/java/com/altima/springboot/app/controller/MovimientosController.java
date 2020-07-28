package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IComercialMovimientoService;

@Controller
public class MovimientosController {
	
	@Autowired
	private IComercialMovimientoService movimientoService;
	
	@GetMapping("/movimientos")
	public String listExits(Model model) {
		
		model.addAttribute("listMovimientos", movimientoService.findAllWithNames());
		return "movimientos";
	}
	@GetMapping("/detalle-movimientos")
	public String listaMovimientos() {
		return "detalle-movimientos";
	}
	
	@GetMapping("/historico-de-muestras")
	public String listHistorial(Model m) {
		m.addAttribute("historicos", movimientoService.findAllHistorico());
		return "historico-de-muestras";
	}
}

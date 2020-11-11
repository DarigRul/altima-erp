package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altima.springboot.app.models.service.IComercialMovimientoService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Controller
public class MovimientosController {
	
	@Autowired
	private IComercialMovimientoService movimientoService;
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_MUESTRARIO_MOVIMIENTOS_LISTAR"})
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
	
	@RequestMapping(value = "/moviminetos-expirados", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> modelo() {
		return movimientoService.findAllExpirados();
	}
}

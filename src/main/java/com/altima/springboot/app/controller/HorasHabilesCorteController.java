package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.access.annotation.Secured;

@Controller
public class HorasHabilesCorteController {

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_LISTAR"})
	@GetMapping("/horas-habiles-corte")
	public String horasHabilesCorte(Model model) {
		return "horas-habiles-corte";
	}
    
}

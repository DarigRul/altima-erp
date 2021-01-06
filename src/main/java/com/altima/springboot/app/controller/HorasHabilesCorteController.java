package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HorasHabilesCorteController {
	@GetMapping("/horas-habiles-corte")
	public String horasHabilesCorte(Model model) {
		return "horas-habiles-corte";
	}
    
}

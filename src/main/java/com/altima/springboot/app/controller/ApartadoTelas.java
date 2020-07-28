package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApartadoTelas {
	@GetMapping("/apartado-de-telas")
	public String listApartado() {
		return "apartado-de-telas";
	}
	@GetMapping("/editar-apartado-de-telas")
	public String editApartado() {
		return "editar-apartado-de-telas";
	}
}

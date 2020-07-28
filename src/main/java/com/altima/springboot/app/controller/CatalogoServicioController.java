package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogoServicioController {

	@GetMapping("/catalogos-servicio")
	public String listCat() {
		return "catalogos-servicio";
	}
}

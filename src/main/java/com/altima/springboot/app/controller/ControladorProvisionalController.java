package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorProvisionalController {
    @GetMapping("/asignacion-ruta-programa")
	public String asignacionRuta() {
		return "asignacion-ruta-programa";
	}
	@GetMapping("/asignacion-programa")
	public String asignacionPrograma() {
		return "asignacion-programa";
	}
	@GetMapping("/tiempos-de-corte")
	public String tiemposCorte() {
		return "tiempos-de-corte";
	}

}

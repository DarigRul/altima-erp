package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarioRutasController {
	
	@GetMapping("agenda-rutas")
	public String calendarRuta() {
		
		return "agenda-rutas";
	}
}

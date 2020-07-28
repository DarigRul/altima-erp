package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntradaSalidaAMPController {
	@GetMapping("/movimientos-amp")
	public String Index()
	{
		return"movimientos-amp";
	}
	
	@GetMapping("/agregar-movimientos-amp")
	public String Store()
	{
		return"agregar-movimientos-amp";
	}
}

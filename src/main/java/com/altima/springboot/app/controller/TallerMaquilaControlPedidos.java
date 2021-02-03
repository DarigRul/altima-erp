package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TallerMaquilaControlPedidos {

	
	@GetMapping("/control-pedidos")
	public String ListControlPedidos() {
		
		return "/control-pedidos";
	}
	
}

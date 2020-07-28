package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PendienteRecibirController {
	@GetMapping("pendiente-por-recibir")
	public String Index()
	{
		return"pendiente-por-recibir";
	}
}

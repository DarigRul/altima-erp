package com.altima.springboot.app.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PendienteSurtirController {
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AMP_PENDIENTESURTIR_LISTAR"})
	@GetMapping("pendiente-por-surtir")
	public String Index()
	{
		return"pendiente-por-surtir";
	}
}

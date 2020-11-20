package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogosAMPController {
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AMP_CATALOGOS_LISTAR"})
	@GetMapping("catalogos-amp")
	public String listCatalogos()
	{
		return"catalogos-amp";
	}

}

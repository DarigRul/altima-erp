package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogosAMPController {
	
	@GetMapping("catalogos-amp")
	public String listCatalogos()
	{
		return"catalogos-amp";
	}

}

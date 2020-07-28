package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogosMaquilaController {
	@GetMapping("catalogos-maquila")
	public String Index()
	{
		return"catalogos-maquila";
	}
}

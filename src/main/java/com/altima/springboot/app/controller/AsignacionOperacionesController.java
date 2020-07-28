package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AsignacionOperacionesController {
	@GetMapping("asignacion-de-operaciones")
	public String Index()
	{
		return"asignacion-de-operaciones";
	}
	
	@GetMapping("asignar-operacion")
	public String AddPerson()
	{
		return"asignar-operacion";
	}
	
}

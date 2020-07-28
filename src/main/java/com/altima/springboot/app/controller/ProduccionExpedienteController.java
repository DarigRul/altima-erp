package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProduccionExpedienteController {
	
	@GetMapping("/cierre-de-expediente")
	public String listExpediente()
	{
		return"cierre-de-expediente";
	}
	
	@GetMapping("/produccion-coordinados")
	public String listCoordinados()
	{
		return"produccion-coordinados";
	}
	@GetMapping("/produccion-coordinados-detalle")
	public String detailCoordinados()
	{
		return"produccion-coordinados-detalle";
	}
	
	@GetMapping("/produccion-empleados")
	public String listEmpleados()
	{
		return"produccion-empleados";
	}
	
	@GetMapping("/produccion-concentrado-de-prendas")
	public String listPrendas()
	{
		return"produccion-concentrado-de-prendas";
	}
	
	@GetMapping("/produccion-concentrado-de-tallas")
	public String listTallas()
	{
		return"produccion-concentrado-de-tallas";
	}
}

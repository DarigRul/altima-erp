package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.service.IComercialSpfEmpleadoService;

@CrossOrigin(origins = { "*" })
@Controller
public class ComercialSpfEmpleadoController {
	
	@Autowired
	private IComercialSpfEmpleadoService SPFService;

	@GetMapping("/empleados-spf/{id}")
	public String ListaEmpleadosSPF(@PathVariable(value = "id") Long id,Model model){
		
		model.addAttribute("empleados", SPFService.empleados(id));
    	return"empleados-spf";
	}
}

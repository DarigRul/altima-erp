package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IHrEmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoRestController {
    
    @Autowired IHrEmpleadoService empleadoService;

    @GetMapping("/rh-editar-empleado")
	public Object editarIncrementos (@RequestParam(name="id") Long id) {
		System.out.println("El id a editar es "+ id);
		return empleadoService.findEmpleadoById(id);
	}
}
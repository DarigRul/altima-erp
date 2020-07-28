package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.service.IHrIncrementoPlazaService;
import com.altima.springboot.app.models.service.IHrPermisoService;

@RestController
public class HrIncrementoPlazaRestController {

	@Autowired
	private IHrIncrementoPlazaService incrementoPlazaService;
	
	//Método para editar plazas
	
	@GetMapping("/rh-editar-incrementos")
	public Object editarIncrementos (@RequestParam(name="id") Long id) {
		System.out.println("El id a editar es "+ id);
		return incrementoPlazaService.editarPlazas(id);
	}
	
	
	//Método para listar departamentos por idArea
	@GetMapping("/rh-listarDepas")
	public Object listarDepas (@RequestParam(name="area") Long id) {
		
		System.out.println("listar depas:" + id);
		return incrementoPlazaService.listarDepartamentos(id);
	}
	
	//Método para listar puestos y sueldos por idDepartamento
	@GetMapping("/rh-listarPuestos")
	public Object listarPuestos (@RequestParam(name="departamento") Long id) {
		
		System.out.println("listar puestos:" + id);
		return incrementoPlazaService.listarPuestos(id);
	}
	
	
	
	
	
	
}

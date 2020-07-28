package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.altima.springboot.app.models.service.IHrPermisoService;

@RestController
public class HrPermisoRestController {

	@Autowired
	private IHrPermisoService servicePermiso;
	
 //Método para listar Departamentos por idArea
	@GetMapping("/rh-listarDepa-Permiso")
	public Object listarDepartamentos (@RequestParam(name="area") Long id) {
			
	System.out.println("Listar Departamentos:" + id);
	return servicePermiso.listarDepartamentos(id);
		}
		
	
	//Método para listar puestos por idDepartamento
	@GetMapping("/rh-listarPuestos-Permiso")
	public Object listarPuestos (@RequestParam(name="departamento") Long id) {
					
	System.out.println("listar puestos:" + id);
	return servicePermiso.listarPuestos(id);
	}
	
	@GetMapping("/rh-filtrar-empleado")
	public Object filtrarEmpleados (@RequestParam(name="id") Long id) {
	System.out.println("listar empleados:" + id);
	return servicePermiso.findEmpleados(id);
	}
	
	
}

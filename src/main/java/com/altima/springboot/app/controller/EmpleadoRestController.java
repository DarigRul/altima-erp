package com.altima.springboot.app.controller;

import java.util.HashMap;
import java.util.Map;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.service.IHrEmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoRestController {
    
    @Autowired IHrEmpleadoService empleadoService;

    @GetMapping("/rh-editar-empleado")
	public Object editarIncrementos (@RequestParam(name="id") Long id) {
		System.out.println("El id a editar es "+ id);
		return empleadoService.findEmpleadoById(id);
	}

	@GetMapping("getEmpleadoById/{id}")
	public ResponseEntity<?> getCliente(@PathVariable(name="id") Long id) {

		
		Map<String, Object> response = new HashMap<>();
		HrEmpleado empleado =null;
		try {
			empleado =empleadoService.findOne(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(empleado == null){
			response.put("mensaje", "La empresa con el id "+ id +" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<HrEmpleado>(empleado,HttpStatus.OK);
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_EMPLEADOS_ELIMINAR"})
	@PutMapping("/bajaEmpleado/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> putEmpresa(@PathVariable(name="id") Long id,@RequestParam String fechaBaja,@RequestParam String motivoBaja) {
		
		Map<String, Object> response = new HashMap<>();
		HrEmpleado empleado=empleadoService.findOne(id);
		if(empleado==null){
			response.put("mensaje", "El empleado con el id "+ id +" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			empleado.setMotivoBaja(motivoBaja);
			empleado.setFechaBaja(fechaBaja);
			empleado.setUltimaFechaModificacion(null);
			empleado.setEstatus("0");
			empleadoService.save(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El empleado se dio de baja corectamente");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
}
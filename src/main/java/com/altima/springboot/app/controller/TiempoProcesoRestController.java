package com.altima.springboot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.dto.TiempoCantidadProcesoDto;
import com.altima.springboot.app.dto.TiemposProcesosDto;
import com.altima.springboot.app.models.entity.ProduccionTiempoFamiliaPrenda;
import com.altima.springboot.app.models.service.IProduccionTiempoFamiliaPrendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tiempoFamiliaPrenda")
public class TiempoProcesoRestController {

	@Autowired
	private IProduccionTiempoFamiliaPrendaService produccionTiempoFamiliaPrendaService;

	@GetMapping("/")
	public List<TiemposProcesosDto> getTiempos() {
		return produccionTiempoFamiliaPrendaService.findTiempoFamiliaPrenda();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTiempo(@PathVariable(name = "id") Long id) {

		Map<String, Object> response = new HashMap<>();
		ProduccionTiempoFamiliaPrenda tiempos = null;
		try {
			tiempos = produccionTiempoFamiliaPrendaService.findOne(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tiempos == null) {
			response.put("mensaje", "La registro con el id " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProduccionTiempoFamiliaPrenda>(tiempos, HttpStatus.OK);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> postTiempo(@RequestBody ProduccionTiempoFamiliaPrenda tiempos) {
		Map<String, Object> response = new HashMap<>();
		try {
			produccionTiempoFamiliaPrendaService.save(tiempos);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProduccionTiempoFamiliaPrenda>(tiempos, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> putTiempo(@RequestBody ProduccionTiempoFamiliaPrenda tiempos,
			@PathVariable(name = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		ProduccionTiempoFamiliaPrenda editTiempos =produccionTiempoFamiliaPrendaService.findOne(id);
		if (editTiempos == null) {
			response.put("mensaje", "La registro con el id " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			editTiempos.setTiempoPrendaCuadros(tiempos.getTiempoPrendaCuadros());
			editTiempos.setTiempoPrendaFantasia(tiempos.getTiempoPrendaFantasia());
			editTiempos.setTiempoPrendaLisa(tiempos.getTiempoPrendaLisa());
			editTiempos.setTiempoPrendaRayasHorizontales(tiempos.getTiempoPrendaRayasHorizontales());
			editTiempos.setTiempoPrendaRayasVerticales(tiempos.getTiempoPrendaRayasVerticales());
			editTiempos.setTiempoTalla(tiempos.getTiempoTalla());
			editTiempos.setTiempoRefilado(tiempos.getTiempoRefilado());
			produccionTiempoFamiliaPrendaService.save(editTiempos);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProduccionTiempoFamiliaPrenda>(tiempos, HttpStatus.CREATED);
	}
}

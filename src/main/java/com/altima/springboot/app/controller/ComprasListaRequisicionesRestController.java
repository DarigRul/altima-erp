package com.altima.springboot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.dto.RequisicionListDto;
import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.models.service.IAmpInventarioService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComprasListaRequisicionesRestController {

	@Autowired
	IAmpInventarioService ampInventarioService;
	@Autowired
	IAmpInventarioProovedorService inventarioProovedorService;

	@GetMapping("getListRequisicion")
	public ResponseEntity<?> getListRequisicion() {

		Map<String, Object> response = new HashMap<>();
		List<RequisicionListDto> inventario = null;
		try {
			inventario = ampInventarioService.findAllRequisicion();

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (inventario.size() == 0) {
			response.put("mensaje", "No hay materiales");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<RequisicionListDto>>(inventario, HttpStatus.OK);
	}

	@GetMapping("getProveedorByIdMaterialAndTipo")
	public ResponseEntity<?> getProveedorByIdMaterialAndTipo(@RequestParam Long id, @RequestParam String tipo) {
		Map<String, Object> response = new HashMap<>();
		JSONArray proveedoresArray = new JSONArray();
		List<Object[]> proveedores = null;

		try {
			proveedores = inventarioProovedorService.View(id, tipo);
			for (Object[] objects : proveedores) {
				JSONObject proveedoresJson = new JSONObject();
				proveedoresJson.put("idInventarioProveedor", objects[0]);
				proveedoresJson.put("idProveedor", objects[1]);
				proveedoresJson.put("claveProveedor", objects[2]);
				proveedoresJson.put("nombreProveedor", objects[4]);
				proveedoresArray.put(proveedoresJson);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (proveedores.size() == 0) {
			response.put("mensaje", "no existen proveedores para el material " + id + " de tipo " + tipo);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(proveedoresArray.toString(), HttpStatus.OK);
	}
}

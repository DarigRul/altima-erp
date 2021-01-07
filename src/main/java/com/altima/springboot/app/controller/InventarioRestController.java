package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.dto.InventarioListDto;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.service.IAmpInventarioService;
import com.altima.springboot.app.models.service.IInventarioService;
import com.altima.springboot.app.models.service.IProduccionDetalleService;

@RestController
public class InventarioRestController {
	@Autowired
	private IInventarioService inventario;	
	
	@Autowired
	private IProduccionDetalleService DetalleService;	
	
	@Autowired
	private IAmpInventarioService inventarioService;
	
	
	
	@RequestMapping(value = "/declinado-detalle-pedido", method = RequestMethod.POST)
	public String guardarImagenes(@RequestParam("id") Long id) {

		
		
		System.out.println("asi resivo en el rest de inventario el id   "+ id);
		String ss = "jaja";
		String dpi = ss;
		System.out.println("si entro al rest de inventario");
		ProduccionDetallePedido objetoDetalle = DetalleService.findOne(id);
		
		objetoDetalle.setEstatus("0");
		
		DetalleService.save(objetoDetalle);
		
        return dpi;
		

	}

	@GetMapping("materiales_all_by_proveedor/{id}")
	public ResponseEntity<?> getProveedor(@PathVariable(name = "id") Long id) {

		Map<String, Object> response = new HashMap<>();
		List<InventarioListDto> inventario=null;
		try {
			inventario=inventarioService.findAllByProveedor(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (inventario.size() == 0) {
			response.put("mensaje", "No hay materiales para el proveedor "+id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<InventarioListDto>>(inventario, HttpStatus.OK);
	}
	

}

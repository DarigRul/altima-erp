package com.altima.springboot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.dto.MaterialFaltanteListDto;
import com.altima.springboot.app.models.service.IPedidosRequisicionMaterialesService;

@RestController
public class PedidosRequisicionMaterialesRestController {

	@Autowired
	public IPedidosRequisicionMaterialesService RequisicionMaterialesService;
	
	 @GetMapping("/getMaterialesFaltantesByIds")
		public ResponseEntity<?> getCliente(@RequestParam String ids) {
	        System.out.println(ids);
			Map<String, Object> response = new HashMap<>();
			List<MaterialFaltanteListDto> materiales =null;
			try {
				materiales =RequisicionMaterialesService.findAllMaterialesFaltantes(ids);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la BD");
				response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(materiales.size()==0){
				response.put("mensaje", "No existen telas faltantes");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<MaterialFaltanteListDto>>(materiales,HttpStatus.OK);
		}
	
	
	
}

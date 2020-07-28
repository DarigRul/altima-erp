package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.service.IInventarioService;
import com.altima.springboot.app.models.service.IProduccionDetalleService;

@RestController
public class InventarioRestController {
	@Autowired
	private IInventarioService inventario;	
	
	@Autowired
	private IProduccionDetalleService DetalleService;	
	
	
	
	
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
	

}

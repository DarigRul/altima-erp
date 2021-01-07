package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.repository.IComprasOrdenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdenComprasController {

	@Autowired
	IComprasOrdenService comprasOrdenService;
	@Autowired
	IAmpInventarioProovedorService proveedorService;
    
    @GetMapping("/orden-de-compra")
	public String ordenCompra(Model m) {
		m.addAttribute("ordenes", comprasOrdenService.findAllList());
		return "orden-de-compra";
	}
	@GetMapping("/orden-de-compra-nueva")
	public String ordenCompraNew(Model m) {
		m.addAttribute("proveedores", proveedorService.Proveedores());
		return "orden-de-compra-nueva";
	}
}

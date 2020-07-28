package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComprasProveedoresController {
    @GetMapping("compras-proveedores")
	public String Proveedores() {
		return"compras-proveedores";
	}

	@GetMapping("compras-agregar-proveedores")
	public String addProveedores() {
		return"compras-agregar-proveedores";
	}
}
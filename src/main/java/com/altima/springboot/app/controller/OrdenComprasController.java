package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.ComprasOrden;
import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.models.service.IComprasOrdenDetalleService;
import com.altima.springboot.app.models.service.IComprasProveedorService;
import com.altima.springboot.app.repository.IComprasOrdenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrdenComprasController {

	@Autowired
	IComprasOrdenService comprasOrdenService;
	@Autowired
	IAmpInventarioProovedorService proveedorService;
	@Autowired
	IComprasProveedorService comprasProveedorService; 
	@Autowired
	IComprasOrdenDetalleService comprasOrdenDetalleService;
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMPRAS_ORDEN_COMPRA_LISTAR"})
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

	@GetMapping("/orden-de-compra/{id}")
	public String ordenCompraEditar(Model m,@PathVariable Long id) {
		ComprasOrden orden= comprasOrdenService.findOne(id);
		m.addAttribute("detalles", comprasOrdenDetalleService.findByIdOrdenCompras(id));
		m.addAttribute("proveedor", comprasProveedorService.findOne(orden.getIdProveedor()));
		m.addAttribute("cabecero", orden);
		return "orden-de-compra-nueva";
	}

	@GetMapping("/orden-compra-reporte/{id}")
	public String ordenCompraReporte(Model m,@PathVariable Long id) {
		ComprasOrden orden= comprasOrdenService.findOne(id);
		m.addAttribute("detalles", comprasOrdenDetalleService.findByIdOrdenCompras(id));
		m.addAttribute("proveedor", comprasProveedorService.findOne(orden.getIdProveedor()));
		m.addAttribute("cabecero", orden);
		return "/orden-compra-reporte";
	}
}

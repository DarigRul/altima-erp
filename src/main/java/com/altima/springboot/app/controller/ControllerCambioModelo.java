package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialHistorialCambioPrendaService;

@Controller
public class ControllerCambioModelo {
	@Autowired
	private IComercialHistorialCambioPrendaService serviceCambio;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	@Autowired
	private ICargaPedidoService cargaPedidoService;
	
	@GetMapping("/empleados-spf-cambio-modelo/{id}")
	public String ListaEmpleadosSPFCambio(@PathVariable(value = "id") Long id,Model model){
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
	
		model.addAttribute("idPrincipal",pedido.getIdPedido());
		model.addAttribute("empleado", serviceCambio.empleadosSPF(id));
		
		model.addAttribute("lista", serviceCambio.vista(id));
    	return"cambio-modelo-falda";
	}
	
}

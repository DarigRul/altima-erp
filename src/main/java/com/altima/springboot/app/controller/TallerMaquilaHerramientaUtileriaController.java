package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IMaquilaAsignacionTicketsService;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoBultoService;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoService;

@Controller
public class TallerMaquilaHerramientaUtileriaController {

	@Autowired
	IMaquilaControlPedidoService maquilaControlPedidoService;
	@Autowired
	IMaquilaControlPedidoBultoService maquilaControlPedidoBultoService;
	@Autowired
	IMaquilaAsignacionTicketsService maquilaAsignacionTicketsService;
	
	@GetMapping("/herramienta-utileria")
	public String HerramientaUtileria(Model model) {
		model.addAttribute("control_pedido", maquilaControlPedidoService.findAllMaquilaControlPedido());
		model.addAttribute("clientes", maquilaControlPedidoService.findAllCliente());
		model.addAttribute("prenda_modelo", maquilaControlPedidoService.findAllPrendaModelo());
	
		return "herramienta-utileria";
	}
	
	
	
	
}

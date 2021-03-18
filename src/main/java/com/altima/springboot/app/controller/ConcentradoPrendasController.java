package com.altima.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.altima.springboot.app.models.service.IComercialConcentradoPrendasService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;

@Controller
public class ConcentradoPrendasController 
{
	@Autowired
	private IComercialConcentradoPrendasService concentradoPrendasService;
	
	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@GetMapping("/concentrado-de-prendas/{id}")
	public String listGeneral(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		
		model.addAttribute("empleados", concentradoPrendasService.findAllEmpleadosByPedido(id));
		model.addAttribute("coordinados", concentradoPrendasService.findCoordinadosfromPedido(id));
		model.addAttribute("idPedido", id);
		
		return "concentrado-de-prendas";
	}

	@GetMapping("/concentrado-de-prendas/expediente/{id}")
	public String listGeneralExpediente(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		
		model.addAttribute("empleados", concentradoPrendasService.findAllEmpleadosByPedido(id));
		model.addAttribute("coordinados", concentradoPrendasService.findCoordinadosfromPedido(id));
		model.addAttribute("idPedido", id);
		model.addAttribute("expediente", "true");
		
		return "concentrado-de-prendas";
	}
}

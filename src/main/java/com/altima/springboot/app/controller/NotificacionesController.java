package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.service.IComercialMovimientoService;

@ControllerAdvice
public class NotificacionesController {
	
	@Autowired
	private IComercialMovimientoService movimientoService;
	
	
	/*
	 * @ModelAttribute("carlos") public ComercialCliente listExits(Model model) {
	 * 
	 * ComercialCliente carlos=new ComercialCliente();
	 * carlos.setApellidoMaterno("amaro"); return carlos; }
	 */
	
	@GetMapping("/notifi")
	@ResponseBody
	public String notificaciones() {
		
		return null;
		
	}
	
	
	
	
	
}

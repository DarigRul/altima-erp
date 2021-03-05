package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IMaquilaAsignacionTicketsService;

@Controller
public class TallerMaquilaAsignacionTicketsController {
@Autowired
IMaquilaAsignacionTicketsService maquilaAsignacionTicketsService;
	
	@GetMapping("/asignacion-tickets")
	public String ListarAsignacionTickets(Model model) {
		
		return "/asignacion-tickets";
	}
	
}

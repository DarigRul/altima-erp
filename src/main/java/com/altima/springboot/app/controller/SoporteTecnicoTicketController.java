package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.ISoporteTecnicoLookupService;
import com.altima.springboot.app.models.service.ISoporteTecnicoTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SoporteTecnicoTicketController {

    @Autowired
    ISoporteTecnicoLookupService soporteTecnicoLookupService;
    @Autowired
    ISoporteTecnicoTicketService soporteTecnicoTicketService; 
    
    @GetMapping("/soporte-tecnico-ticket")
    public String listarSoporteTecnincoTicket(Model m) {
        m.addAttribute("categorias", soporteTecnicoLookupService.findAllByType("Categoria"));
        m.addAttribute("tickets", soporteTecnicoTicketService.findAllTicket());
        return "soporte-tecnico-ticket";
    }
}

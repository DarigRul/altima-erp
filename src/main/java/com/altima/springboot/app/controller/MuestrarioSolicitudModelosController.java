package com.altima.springboot.app.controller;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.ComercialAgentesVenta;
import com.altima.springboot.app.models.service.IComercialAgentesVentaService;
import com.altima.springboot.app.models.service.IComercialSolicitudModeloService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MuestrarioSolicitudModelosController {

    @Autowired
    IComercialSolicitudModeloService solicitudModeloService;

    @Autowired
    IComercialAgentesVentaService agentesVentaService;

    @Autowired
    AuthComponent authC;

    @GetMapping("solicitud-de-modelos")
    public String index(Model m){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialAgentesVenta agente= agentesVentaService.findOne(authC.currentemployeeid());
        if (agente==null) {
            m.addAttribute("auth", "");
            m.addAttribute("solicitudes", solicitudModeloService.findAllSolicitud(0L));
        } else {
            m.addAttribute("auth", authC.currentemployeeid());
            m.addAttribute("solicitudes", solicitudModeloService.findAllSolicitud(authC.currentemployeeid()));
        }
        return "solicitud-de-modelos";
    }
}

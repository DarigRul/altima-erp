package com.altima.springboot.app.controller;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.service.IComercialSolicitudCambioFechaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CambioFechaPedidoController {

    @Autowired
    IComercialSolicitudCambioFechaService cambioFechaService;

    @Autowired
    AuthComponent auth;

    @GetMapping("/cambio-fecha-pedido")
    public String CambioFechaPedido(Model m)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String roles =auth.getAuthorities().toString();
        if (roles.contains("ROLE_COMERCIAL_SOLICITUD_CAMBIO_FECHA_GERENCIA")||roles.contains("ROLE_ADMINISTRADOR")) {
            m.addAttribute("solicitudes", cambioFechaService.findAllDetalle(0L));
        } else {

            m.addAttribute("solicitudes", cambioFechaService.findAllDetalle(this.auth.currentemployeeid()));
        }
        m.addAttribute("auth", this.auth.currentemployeeid());
        return"cambio-fecha-pedido";
    }
}
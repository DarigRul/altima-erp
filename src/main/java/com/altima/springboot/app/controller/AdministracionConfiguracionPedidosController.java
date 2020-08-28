package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdministracionConfiguracionPedidosController {
    @GetMapping(value="/configuracion-de-pedidos")
    public String ConfiguracionPedidosList() {
        return "configuracion-de-pedidos";
    }
    
}
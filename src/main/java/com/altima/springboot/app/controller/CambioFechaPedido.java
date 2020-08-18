package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CambioFechaPedido {
    @GetMapping("/cambio-fecha-pedido")
    public String CambioFechaPedido()
    {
        return"cambio-fecha-pedido";
    }
}
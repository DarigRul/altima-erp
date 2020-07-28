package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdenesProduccionController {
    @GetMapping("ordenes-produccion-pedido")
    public String rutaProgramaPedido() {
        return "ordenes-produccion-pedido";
    }
}
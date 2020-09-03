package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComercialCatalogosController {
    @GetMapping("/catalogos-comercial")
    public String CatalogosComercialList() {
        return"catalogos-comercial";
    }
}

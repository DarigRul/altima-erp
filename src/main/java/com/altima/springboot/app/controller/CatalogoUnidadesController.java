package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogoUnidadesController {
    @GetMapping("/logistica-catalogos-unidades")
    public String CatalogosUnidades()
    {
        return"logistica-catalogos-unidades";
    }
}
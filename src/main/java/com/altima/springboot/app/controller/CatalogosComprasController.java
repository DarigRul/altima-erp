package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogosComprasController {
    @GetMapping("catalogos-compras")
    public String Catalogos()
    {
        return"catalogos-compras";
    }
}
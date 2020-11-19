package com.altima.springboot.app.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComercialCatalogosController {
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_CATALOGOS_LISTAR"})
    @GetMapping("/catalogos-comercial")
    public String CatalogosComercialList() {
        return"catalogos-comercial";
    }
}

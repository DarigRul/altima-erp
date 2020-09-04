package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministracionCargaMasivaPrecios {
    @GetMapping("/carga-masiva-precios")
    public String cargaMasivaPrecios(){
        return"carga-masiva-precios";
    }
}

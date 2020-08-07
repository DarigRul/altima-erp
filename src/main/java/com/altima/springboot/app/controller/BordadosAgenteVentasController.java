package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BordadosAgenteVentasController {
    @GetMapping("/bordados")
    public String listBordados() {
        return "bordados";
    }

    @GetMapping("/agregar-bordado")
    public String addBordados() {
        return "agregar-bordado";
    }
}
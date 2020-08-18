package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServicioClienteController {
    @GetMapping("/servicio-cliente")
    public String ServicioCliente()
    {
        return"servicio-cliente";
    }
    @GetMapping("/servicio-cliente-solicitud")
    public String ServicioClienteSol()
    {
        return"servicio-cliente-solicitud";
    }
}
package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MuestrarioSolicitudModelos {
    @GetMapping("solicitud-de-modelos")
    public String index()
    {
        return "solicitud-de-modelos";
    }
}

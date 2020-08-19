package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SolicitudCamionetaController {
    @GetMapping("/logistica-solicitud-camionetas")
    public String CamionetasSolicitud()
    {
        return"logistica-solicitud-camionetas";
    }
}
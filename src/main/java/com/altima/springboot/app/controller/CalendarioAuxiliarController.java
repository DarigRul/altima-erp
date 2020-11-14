package com.altima.springboot.app.controller;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarioAuxiliarController {
    @Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AUXILIARVENTAS_CALENDARIO_LISTAR"})
    @GetMapping("/calendario-auxiliar")
    public String CalendarioGeneral()
    {
        return "calendario-auxiliar";
    }
}
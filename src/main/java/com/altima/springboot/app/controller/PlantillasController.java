package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlantillasController {
    @GetMapping("/plantilla-basica")
    public String plantillaBasica() {
        return"/plantillas/plantilla-basica";
    }
    @GetMapping("/plantilla-scroll")
    public String plantillaScroll() {
        return"/plantillas/plantilla-scroll";
    }
    @GetMapping("/plantilla-filtrado")
    public String plantillaFiltrado() {
        return"/plantillas/plantilla-filtrado";
    }
    @GetMapping("/plantilla-scroll-filtrado")
    public String plantillaScrollFiltrado() {
        return"/plantillas/plantilla-scroll-filtrado";
    }
    @GetMapping("/plantilla-botones")
    public String plantillaBotones() {
        return"/plantillas/plantilla-botones";
    }
    @GetMapping("/plantilla-formulario")
    public String plantillaFormulario() {
        return"/plantillas/plantilla-formulario";
    }
}

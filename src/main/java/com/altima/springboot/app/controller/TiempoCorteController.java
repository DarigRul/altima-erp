package com.altima.springboot.app.controller;


import com.altima.springboot.app.models.service.ITiempoCorteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TiempoCorteController {

    @Autowired
    private ITiempoCorteService TiempoService;

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_TIEMPO_CORTE"})
    @GetMapping("/tiempos-de-corte")
	public String tiemposCorte(Model model) {

        model.addAttribute("view", TiempoService.view());
		return "tiempos-de-corte";
	}

    
}

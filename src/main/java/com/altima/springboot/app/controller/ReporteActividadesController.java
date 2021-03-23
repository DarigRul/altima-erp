package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IServicioClienteLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ReporteActividadesController {
    
    @Autowired
	private IServicioClienteLookupService servicioClienteService;
    
    @GetMapping(value="/reporte-actividades")
    public String view(Model model) {
        model.addAttribute("procesos", servicioClienteService.findAllLookup("Proceso"));
        model.addAttribute("procesostable", servicioClienteService.listaProcesos());
        return "reporte-actividades";
    }
    
}

package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IServicioClienteRecepcionDevolucionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecepcionDevolucionController {
    @Autowired
    private IServicioClienteRecepcionDevolucionService recepcionDevolucionService;

    @GetMapping("/recepcion-devolucion")
    public String view (Model model){
        model.addAttribute("maquileros", recepcionDevolucionService.maquileros());
        model.addAttribute("view", recepcionDevolucionService.viewPricipal());
        return "recepcion-devolucion";
    }
    
}

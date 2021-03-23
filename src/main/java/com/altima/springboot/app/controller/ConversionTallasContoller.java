package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IServicioClienteConversionTallasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ConversionTallasContoller {

    @Autowired
    private IServicioClienteConversionTallasService serviceTallas;

    @GetMapping("/conversion-tallas")
    public String view (Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth.getName().equals("ADMIN")){
            model.addAttribute("ADMIN", "1");
        }else{
            model.addAttribute("ADMIN", "0");
        }
        model.addAttribute("pedidos", serviceTallas.view());
        return "consersion-tallas";
    }
    
}

package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComprasRequisicionTelasController {
    @GetMapping("/requisicion-de-telas")
    public String RequisicionTelasList(){
        return"requisicion-de-telas";
    }
}
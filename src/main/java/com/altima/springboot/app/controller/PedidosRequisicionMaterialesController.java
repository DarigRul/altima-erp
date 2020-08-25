package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidosRequisicionMaterialesController {
    @GetMapping("/requisicion-de-materiales")
    public String RequisicionMaterialesList(){
        return"requisicion-de-materiales";
    }
}
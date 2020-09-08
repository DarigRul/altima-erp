package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequisicionComprasController {
    @GetMapping("/requisicion-de-compras")
    public String RequisicionComprasList(){
        return"requisicion-de-compras";
    }
    @GetMapping("/requisicion-de-compras-nueva")
    public String RequisicionComprasAdd(){
        return"requisicion-de-compras-nueva";
    }
}
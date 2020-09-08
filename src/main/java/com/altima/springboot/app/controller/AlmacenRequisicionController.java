package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlmacenRequisicionController {
    @GetMapping("/requisicion-de-almacen")
    public String RequisicionAlmacenList(){
        return"requisicion-de-almacen";
    }
    @GetMapping("/requisicion-de-almacen-nueva")
    public String RequisicionAlmacenAdd(){
        return"requisicion-de-almacen-nueva";
    }
}
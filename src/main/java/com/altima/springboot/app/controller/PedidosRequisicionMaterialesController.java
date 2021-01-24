package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IPedidosRequisicionMaterialesService;

@Controller
public class PedidosRequisicionMaterialesController {
	@Autowired
	private IPedidosRequisicionMaterialesService PedidosRequisicionMaterialesService;
	
    @GetMapping("/requisicion-de-materiales")
    public String RequisicionMaterialesList(Model model){
    	model.addAttribute("Materiales", PedidosRequisicionMaterialesService.findAllMaterialesFaltantes());
    	
        return"requisicion-de-materiales";
    }
    @GetMapping("/requisicion-de-materiales-pedidos")
    public String RequisicionMaterialesInfo(){
        return"requisicion-de-materiales-pedidos";
    }
}
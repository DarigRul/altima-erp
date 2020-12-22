package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IEmpalmeTelasService;
import com.altima.springboot.app.models.service.IProduccionLookupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class EmpalmeTelasController {

    @Autowired
    private IEmpalmeTelasService EmpalmeService;
    @Autowired
    private IProduccionLookupService ProduccionLookup;


    @GetMapping("/empalme-telas")
	public String empalmeTela (Model model) {
        System.out.println("x");
        model.addAttribute("view", EmpalmeService.view());
        model.addAttribute("SelectRuta", ProduccionLookup.findAllByType("Ruta"));
		return "empalme-telas";
	}
    
    
    
}

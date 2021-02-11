package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IEmpalmeTelasService;
import com.altima.springboot.app.models.service.IProduccionLookupService;
import org.springframework.security.access.annotation.Secured;
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


    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_EMPALME_TELAS"})
    @GetMapping("/empalme-telas")
	public String empalmeTela (Model model) {
        System.out.println("x");
        model.addAttribute("view", EmpalmeService.view());
        model.addAttribute("SelectRuta", ProduccionLookup.findAllByType("Ruta"));
        model.addAttribute("load", "empalme");
		return "empalme-telas";
	}

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_PROGRAMAR_TELAS"})
    @GetMapping("/programar-telas")
	public String programarTela (Model model) {
        System.out.println("x");
        model.addAttribute("view", EmpalmeService.view());
        model.addAttribute("SelectRuta", ProduccionLookup.findAllByType("Ruta"));
        model.addAttribute("load", "programar");
		return "empalme-telas";
	}
    
    
    
}

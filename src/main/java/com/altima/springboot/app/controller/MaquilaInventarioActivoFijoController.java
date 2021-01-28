package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.service.IMaquilaInventarioActivoFijoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MaquilaInventarioActivoFijoController {

    @Autowired
    private IMaquilaInventarioActivoFijoService inventarioService;

    @RequestMapping(value = { "/inventario-activo-fijo" }, method = RequestMethod.GET)
	public String view(Model model) {
        model.addAttribute("view", inventarioService.view());
		return "inventario-activo-fijo";
	}
    
}

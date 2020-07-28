package com.altima.springboot.app.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.service.IInventarioService;
import com.altima.springboot.app.models.service.IProduccionDetalleService;
import com.altima.springboot.app.models.service.IUploadService;

@Controller
public class CatalogosPrendaAgenteController {

    @Autowired
	private IProduccionDetalleService pedidoDetalles;

	@Autowired
	private IInventarioService inventarioService;

    @GetMapping("/catalogos-prendas-agentes")
	public String listPre(Model model) {
        model.addAttribute("muestrario", inventarioService.listCatalogoInventario());

		model.addAttribute("prendas", pedidoDetalles.selectDinamicPrenda());

		model.addAttribute("generos", pedidoDetalles.selectGenero());
		return "catalogos-prendas-agentes";
	}
}
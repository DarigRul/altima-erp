package com.altima.springboot.app.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.service.IProduccionDetalleService;
import com.altima.springboot.app.models.service.IUploadService;

@Controller
public class MuestrarioCatalogoController {

	@Autowired
	private IProduccionDetalleService pedidoDetalles;
	@Autowired
	private IUploadService uploadService;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uploadService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_MUESTRARIO_CATALOGO_LISTAR"})
	@GetMapping("/catalogos-muestrario")
	public String listCatalogo(Model model) {

		model.addAttribute("muestrario", pedidoDetalles.muestrariosCatalogo());

		model.addAttribute("prendas", pedidoDetalles.selectDinamicPrenda());

		model.addAttribute("generos", pedidoDetalles.selectGenero());

		return "catalogos-muestrario";
	}
}

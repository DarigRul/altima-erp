package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.validation.Valid;

import com.altima.springboot.app.models.entity.LogisticaUnidad;
import com.altima.springboot.app.models.service.ILogisticaUnidadesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CatalogoUnidadesController {

    @Autowired
	ILogisticaUnidadesService loguniService;
    
    @GetMapping("/logistica-catalogos-unidades")
	public String CatalogosUnidades(Model model, Map<String, Object> m) throws InterruptedException {
		model.addAttribute("puestos", loguniService.findAllPosition());
		model.addAttribute("unidades", loguniService.findAll());
		LogisticaUnidad unidad = new LogisticaUnidad();
		model.addAttribute("unidad",unidad);
		return"logistica-catalogos-unidades";
	}
	
	@PostMapping("/logistica-catalogos-unidades")
	public String SendUnidad(@Valid @ModelAttribute("unidad") LogisticaUnidad unidad, BindingResult result, Model model) {
		unidad.setEstatus("1");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		unidad.setCreadoPor(auth.getName());
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		unidad.setFechaCreacion(fechaConHora.format(now));
		loguniService.save(unidad);
		if (result.hasErrors()) {
			return "logistica-catalogos-unidades";
		}
		model.addAttribute("unidad",unidad);
		return"redirect:/logistica-catalogos-unidades";
	}
}
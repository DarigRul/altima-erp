package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioListaPrecioPrenda;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.service.IDisenioListaPrecioPrendaService;
import com.altima.springboot.app.models.service.IDisenioPrendaService;

@Controller
public class PrecioPrendaController {
	@Autowired
	IDisenioListaPrecioPrendaService disenioListaPrecioPrendaService;
	@Autowired
	IDisenioPrendaService prendaService;

	@GetMapping("/precio-prenda/{id}")
	public String infoClothesPrice(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		DisenioListaPrecioPrenda precio = disenioListaPrecioPrendaService.findByidPrenda(id);
		DisenioPrenda prenda = prendaService.findOne(id);
		precio.setIdPrenda(id);
		model.addAttribute("idText", prenda.getIdText());
		model.addAttribute("precio", precio);
		return "precios-prenda";
	}

	@PostMapping("/guardar-precio-prenda")
	public String guardarPrecioPrenda(DisenioListaPrecioPrenda precio, RedirectAttributes redirectAttrs) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("aqui esta el id" + precio.getPrecioLocalNuevo());
		if (precio.getIdListaPrecioPrenda() != null) {
			precio.setActualizadoPor(auth.getName());
			precio.setUltimaFechaModificacion(hourdateFormat.format(date));
		} else {
			precio.setCreadoPor(auth.getName());
		}
		disenioListaPrecioPrendaService.save(precio);
		redirectAttrs.addFlashAttribute("title", "Precios insertados correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/prendas";
	}

	@GetMapping("costo-prenda/{id}")
	public String costoPrenda(@PathVariable(value = "id") Long id, Model model) {

		DisenioListaPrecioPrenda precio = disenioListaPrecioPrendaService.findByidPrenda(id);
		precio.setIdPrenda(id);
		model.addAttribute("precio", precio);
		return "costo-prenda";
	}
}

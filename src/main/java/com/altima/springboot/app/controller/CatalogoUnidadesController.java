package com.altima.springboot.app.controller;
import java.util.Map;
import com.altima.springboot.app.models.entity.LogisticaUnidad;
import com.altima.springboot.app.models.service.ILogisticaUnidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
package com.altima.springboot.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.service.ReporteCatalogoService;
//import com.google.gson.Gson;

@CrossOrigin(origins = { "*" })
@Controller
public class ReporteCatalogoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	String date = simpleDateFormat.format(new Date());
	
	@Autowired
	ReporteCatalogoService catalogo;
	
	@RequestMapping(value = { "/reporte-catalogo" }, method = RequestMethod.GET)
	public String reporte(Model model, RedirectAttributes flash) {
		
		List<String[]> listLookups = catalogo.findAllLookup();
		
		model.addAttribute("listLookups", listLookups);
		
		return "reporte-catalogo";
	}
	
	@RequestMapping(value = { "/reporte-catalogo" }, method = RequestMethod.POST)
	
	public String reporteSave(Model model, @RequestParam(name = "cata", required = false) String cata,
			@RequestParam(name = "palabra", required = false) String palabra,
			@RequestParam(name = "fecha1", required = false) String fecha1,
			@RequestParam(name = "fecha2", required = false) String fecha2) {
		
		int link = 0;
		
		if (cata.length() != 0 && palabra.length() != 0 && fecha1.length() != 0 && fecha2.length() != 0) {
			
			model.addAttribute("catalogoFiltro", catalogo.findFiltroReporte(cata, palabra, fecha1, fecha2));
			List<String[]> listLookups = catalogo.findAllLookup();
			model.addAttribute("listLookups", listLookups);
			model.addAttribute("cata", cata);
			model.addAttribute("palabra", palabra);
			model.addAttribute("fecha1", fecha1);
			model.addAttribute("fecha2", fecha2);
			link = 1;
			model.addAttribute("link", link);
			
		} else {
			
			if (cata.length() != 0 && palabra.length() == 0 && fecha1.length() != 0 && fecha2.length() != 0) {
				
				model.addAttribute("catalogoFiltro", catalogo.findFiltroReporteSinpalabra(cata, fecha1, fecha2));
				List<String[]> listLookups = catalogo.findAllLookup();
				model.addAttribute("listLookups", listLookups);
				model.addAttribute("cata", cata);
				model.addAttribute("palabra", palabra);
				model.addAttribute("fecha1", fecha1);
				model.addAttribute("fecha2", fecha2);
				link = 2;
				model.addAttribute("link", link);
				
			} else {
				if (cata.length() != 0 && palabra.length() != 0 && fecha1.length() == 0 && fecha2.length() == 0) {
					model.addAttribute("catalogoFiltro", catalogo.findFiltroReporteSinFechas(cata, palabra));
					List<String[]> listLookups = catalogo.findAllLookup();
					model.addAttribute("listLookups", listLookups);
					model.addAttribute("cata", cata);
					model.addAttribute("palabra", palabra);
					model.addAttribute("fecha1", fecha1);
					model.addAttribute("fecha2", fecha2);
					link = 3;
					model.addAttribute("link", link);
					
				} else {
					model.addAttribute("catalogoFiltro", catalogo.findFiltroReporteSinFePa(cata));
					List<String[]> listLookups = catalogo.findAllLookup();
					model.addAttribute("listLookups", listLookups);
					model.addAttribute("cata", cata);
					model.addAttribute("palabra", palabra);
					model.addAttribute("fecha1", fecha1);
					model.addAttribute("fecha2", fecha2);
					link = 4;
					model.addAttribute("link", link);
				}
			}
		}
		return "reporte-catalogo";
	}
	
	@RequestMapping(value = { "/imprimirleo/{cata}/{palabra}/{fecha1}/{fecha2}/{link}" }, method = RequestMethod.GET)
	public String imp(@PathVariable(value = "cata") String cata, @PathVariable(value = "palabra") String palabra,
			@PathVariable(value = "fecha1") String fecha1, @PathVariable(value = "fecha2") String fecha2,
			@PathVariable(value = "link") int link, Model model) {
		
		if (link == 1) {
			List<DisenioLookup> lookup1 = catalogo.findFiltroReporte(cata, palabra, fecha1, fecha2);
			model.addAttribute("lookup", lookup1);
			
		} else {
			if (link == 2) {
				List<DisenioLookup> lookup1 = catalogo.findFiltroReporteSinpalabra(cata, fecha1, fecha2);
				model.addAttribute("lookup", lookup1);
				
			} else {
				if (link == 3) {
					List<DisenioLookup> lookup1 = catalogo.findFiltroReporteSinFechas(cata, palabra);
					model.addAttribute("lookup", lookup1);
					
				} else {
					List<DisenioLookup> lookup1 = catalogo.findFiltroReporteSinFePa(cata);
					model.addAttribute("lookup", lookup1);
				}
			}
		}
		return "/imprimir";
	}
}

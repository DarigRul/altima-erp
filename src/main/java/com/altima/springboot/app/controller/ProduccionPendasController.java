package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.service.IDisenioPrendaService;
import com.altima.springboot.app.models.service.IProduccionLookupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProduccionPendasController {


	@Autowired
    IDisenioPrendaService disenioPrendaService;
    
    @Autowired
	IProduccionLookupService LookupService;

	@GetMapping("produccion-prendas")
	public String listClothes(Model model, Map<String, Object> m) throws InterruptedException {
        model.addAttribute("prendas", disenioPrendaService.findAllMinR());
		return "produccion-prendas";
    }
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_DISENIO_PRENDAS_REGRESAR_PRODUCCION"})
	@GetMapping("regresar_prenda_produccion/{id}")
	public String regresarPrenda(@PathVariable Long id,Model model) {
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		prenda.setMostrar(false);
		prenda.setFechaDevolucionProduccion(currentDate());
		disenioPrendaService.save(prenda);
		return "redirect:/produccion-prendas";
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_DISENIO_PRENDAS_CONFIRMAR_PRODUCCION"})
	@GetMapping("recibir_prenda_produccion/{id}")
	public String recibirPrenda(@PathVariable Long id,Model model) {
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		prenda.setMostrar(true);
		prenda.setFechaRecepcionProduccion(currentDate());
		disenioPrendaService.save(prenda);
		return "redirect:/produccion-prendas";
    }
    
	@GetMapping("agregarIdRuta/{id}/{idRuta}")
	public String setIdRuta(@PathVariable Long id,@PathVariable Long idRuta,Model model) {
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		prenda.setIdRuta(idRuta);
		disenioPrendaService.save(prenda);
		return "redirect:/produccion-prendas";
    }

    private String currentDate() {
        Date date = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        hourdateFormat.setTimeZone(timeZone);
        String sDate = hourdateFormat.format(date);
        return sDate;
    }
    
}

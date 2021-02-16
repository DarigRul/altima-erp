package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.entity.ProduccionPrendaHistorico;
import com.altima.springboot.app.models.service.IDisenioPrendaService;
import com.altima.springboot.app.models.service.IProduccionLookupService;
import com.altima.springboot.app.models.service.IProduccionPrendaHistoricoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduccionPendasController {


	@Autowired
    IDisenioPrendaService disenioPrendaService;
    
    @Autowired
	IProduccionLookupService LookupService;

	@Autowired
	IProduccionPrendaHistoricoService prendaHistoricoService; 

	@GetMapping("produccion-prendas")
	public String listClothes(Model model, Map<String, Object> m) throws InterruptedException {
        model.addAttribute("prendas", disenioPrendaService.findAllMinR());
		if (disenioPrendaService.countRutas()>0) {
			System.out.println("true");
			model.addAttribute("ifRutas", true);
		} else {
			System.out.println("false");
			model.addAttribute("ifRutas", false);
		}
		
		return "produccion-prendas";
    }
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_DISENIO_PRENDAS_REGRESAR_PRODUCCION"})
	@GetMapping("regresar_prenda_produccion/{id}")
	public String regresarPrenda(@PathVariable Long id,Model model,@RequestParam String devolucion) {
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		ProduccionPrendaHistorico historico =new ProduccionPrendaHistorico();
		prenda.setMostrar(false);
		prenda.setFechaDevolucionProduccion(currentDate());
		// prenda.setDevolucion(devolucion);
		disenioPrendaService.save(prenda);
		historico.setComentario(devolucion);
		historico.setIdPrenda(id);
		historico.setFecha(currentDate());
		historico.setTipo("devolver");
		historico.setCreadoPor(userName());
		prendaHistoricoService.save(historico);
		return "redirect:/produccion-prendas";
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_DISENIO_PRENDAS_CONFIRMAR_PRODUCCION"})
	@GetMapping("recibir_prenda_produccion/{id}")
	public String recibirPrenda(@PathVariable Long id,Model model,@RequestParam(required = false, defaultValue = "0") Long idRuta) {
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		ProduccionPrendaHistorico historico =new ProduccionPrendaHistorico();
		prenda.setMostrar(true);
		prenda.setFechaRecepcionProduccion(currentDate());
		if (idRuta!=0) {
			prenda.setIdRuta(idRuta);
		}
		disenioPrendaService.save(prenda);
		historico.setIdPrenda(id);
		historico.setFecha(currentDate());
		historico.setTipo("recibir");
		historico.setCreadoPor(userName());
		prendaHistoricoService.save(historico);
		return "redirect:/produccion-prendas";
    }
    
	@GetMapping("agregarIdRuta/{id}/{idRuta}")
	public String setIdRuta(@PathVariable Long id,@PathVariable Long idRuta,Model model) {
		DisenioPrenda prenda = disenioPrendaService.findOne(id);
		prenda.setIdRuta(idRuta);
		disenioPrendaService.save(prenda);
		return "redirect:/produccion-prendas";
    }



	@GetMapping("revisarPrenda/{id}")
	public String revisarPrenda(@PathVariable Long id) {
		ProduccionPrendaHistorico historico =new ProduccionPrendaHistorico();
		historico.setIdPrenda(id);
		historico.setFecha(currentDate());
		historico.setTipo("revisar");
		historico.setCreadoPor(userName());
		prendaHistoricoService.save(historico);
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

	private String userName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
    
}

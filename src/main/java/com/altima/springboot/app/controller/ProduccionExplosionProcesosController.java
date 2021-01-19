package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;

@Controller
public class ProduccionExplosionProcesosController {

	@Autowired
	private IProduccionExplosionProcesosService explosionService;
	
	@GetMapping("/explosion-de-procesos")
	public String explosionProcesos(Model model) {
		
		model.addAttribute("listExplosiones", explosionService.findProgramas());
		
		return "explosion-de-procesos";
	}
    
	
	@PostMapping("/guardar-explosion-procesos")
	@Transactional
	@ResponseBody
	public String explosionProcesos (String programa, RedirectAttributes redirectAttrs) {
		
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String validador = "existen registros";
			
			List<Object[]> listaParaExplosion = explosionService.findAllByPrograma(programa);
			if(listaParaExplosion.size()>=1) {
				for (int i = 0; i< listaParaExplosion.size(); i++) {
					Object[] registro = listaParaExplosion.get(i);
					ProduccionExplosionProcesos explosionProcesos = new ProduccionExplosionProcesos();
					
					explosionProcesos.setIdPedido(Long.parseLong(registro[0].toString()));
					explosionProcesos.setIdCoordinado(Long.parseLong(registro[1].toString()));
					explosionProcesos.setClaveProceso(Long.parseLong(registro[6].toString()));
					explosionProcesos.setClavePrenda(Long.parseLong(registro[3].toString()));
					explosionProcesos.setFechaExplosion(dtf.format(now));
					explosionProcesos.setPrograma(programa);
					explosionProcesos.setEstatusProceso(0);
					
					explosionService.save(explosionProcesos);
				
				}System.out.println("si jhalo hasta aca");
			}
			else {
				System.out.println("no hay registros");
				validador = "no hay registros";
			}
			
			if (validador.equalsIgnoreCase("existen registros")) {
				redirectAttrs.addFlashAttribute("title", "Se ha realizado la explosión")
				.addFlashAttribute("icon", "success");
				return "1";
			}
			else {
				redirectAttrs.addFlashAttribute("title", "No existe ese programa en el sistema")
				.addFlashAttribute("icon", "warning");
				return "2";
				
			}
		}
		catch(Exception e) {
			System.out.println(e);
			redirectAttrs.addFlashAttribute("title", "Algo debió salir mal, intente más tarde")
			.addFlashAttribute("icon", "warning");
			return "3";
		}
		finally {
			
		}
	}
}

//ualtima2010_erp_web2
//Y?M1#%2$gc!u
//
//ualtima2010_erp_web3
//B=W;Z3Y^rs[q

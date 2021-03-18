package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;
import com.altima.springboot.app.models.service.IProduccionLookupService;
import com.altima.springboot.app.models.service.IProduccionPermisoUsuarioProcesoService;
import com.altima.springboot.app.models.service.IUsuarioService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ProduccionControlAvancesController {

	@Autowired
	IProduccionLookupService lookupService;
	
	@Autowired
	IProduccionExplosionProcesosService explosionService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IProduccionPermisoUsuarioProcesoService permisoService;
	
	@GetMapping("control-avances")
	public String controlAvances(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
		
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
		if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("listProcesos", permisoService.listarProcesosDisponiblesAdmin());
		} else {
			
			model.addAttribute("listProcesos", permisoService.listarProcesosDisponiblesUser(iduser));

		}
		
		return "control-avances";
	}
	
	@GetMapping("/finalizarProceso/{idExplosion}")
	public String finalizarProceso (@PathVariable(value="idExplosion")Long idExplosion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		ProduccionExplosionProcesos explosion = explosionService.findOne(idExplosion);
		System.out.println("entra?");
		explosion.setEstatusProceso(2);
		explosion.setFechaFin(hourdateFormat.format(date));
		
		explosionService.save(explosion);
		
		return "redirect:/control-avances";
	}
}

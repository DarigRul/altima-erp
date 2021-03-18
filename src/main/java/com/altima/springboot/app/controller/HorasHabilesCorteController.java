package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.altima.springboot.app.models.entity.Usuario;
	import com.altima.springboot.app.models.service.IProduccionPermisoUsuarioProcesoService;
import com.altima.springboot.app.models.service.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class HorasHabilesCorteController {

	@Autowired
	private IProduccionPermisoUsuarioProcesoService permisoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Secured({ "ROLE_ADMINISTRADOR"})
	@GetMapping("/horas-habiles-corte")
	public String horasHabilesCorte(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
		if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("listProcesos", permisoService.listarProcesosDisponiblesAdmin());
		} else {
			
			model.addAttribute("listProcesos", permisoService.listarProcesosDisponiblesUser(iduser));

		}
		return "horas-habiles-corte";
	}

}

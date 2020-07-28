package com.altima.springboot.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Controller
public class CalendarioAgenteController {
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	private IComercialClienteService ClienteService;
	
	@GetMapping("agenda-agente")
	public String calendarAgente( HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/*Obtener todos los datos del usuario logeado*/
		Usuario user=usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser=user.getIdUsuario();
		String role="[ROLE_ADMINISTRADOR]";
		if(auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("empresa", ClienteService.findAllEstatus1(null));
		}else {
			model.addAttribute("empresa", ClienteService.findAllEstatus1(iduser));
		}
		
		return "agenda-agente";
	}
}//jjjjjjjj

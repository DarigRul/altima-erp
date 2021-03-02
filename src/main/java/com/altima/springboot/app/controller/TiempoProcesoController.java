package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IEmpalmeTelasService;
import com.altima.springboot.app.models.service.ITiempoCorteService;
import com.altima.springboot.app.models.service.IUsuarioService;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TiempoProcesoController {
    @Autowired
	private IUsuarioService usuarioService;

    @Autowired
    private IEmpalmeTelasService EmpalmeService;

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_TIEMPO_CORTE"})
    @GetMapping("/tiempos-de-procesos")
	public String tiemposProcesos(Model model) {

        /* Obtener todos los datos del usuario logeado */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
		if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("listProcesos", EmpalmeService.listarProcesosDisponiblesAdmin());
		} else {
			
			model.addAttribute("listProcesos", EmpalmeService.listarProcesosDisponiblesUser(iduser));

		}
        return "tiempos-de-procesos";
	}
    
}

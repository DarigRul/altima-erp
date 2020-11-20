package com.altima.springboot.app.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraspasosAMPController {
		
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AMP_ENTRADASSALIDAS_TRASPASO"})
	@GetMapping("traspasos-multialmacen-amp")
	public String Traspasos() {
		return"traspasos-multialmacen-amp";
	}
	

}

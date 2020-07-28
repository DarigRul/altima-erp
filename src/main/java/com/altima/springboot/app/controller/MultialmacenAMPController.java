package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MultialmacenAMPController {
	
	@GetMapping("multialmacen-amp")
	public String Index() {
		return"multialmacen-amp";
	}
	
	@GetMapping("traspasos-multialmacen-amp")
	public String Traspasos() {
		return"traspasos-multialmacen-amp";
	}
	

}

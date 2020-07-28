package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PendienteSurtirController {
	@GetMapping("pendiente-por-surtir")
	public String Index()
	{
		return"pendiente-por-surtir";
	}
}

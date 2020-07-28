package com.altima.springboot.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.service.JpaUserDetailsService;

@Controller
public class LoginController {
	
	@Autowired
	JpaUserDetailsService jpaUserDetailsService;
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {
		if (error != null) {
			System.out.println("++++++" + jpaUserDetailsService.getX() + "++++++");
			if (jpaUserDetailsService.getX() == null) {
				model.addAttribute("error", "El usuario o la contraseña son incorrectos");
			} else
				model.addAttribute("error", jpaUserDetailsService.getX());
		}
		return "login";
	}
	
	@GetMapping("/")
	public String Index()
	{
		return("index");
	}
}

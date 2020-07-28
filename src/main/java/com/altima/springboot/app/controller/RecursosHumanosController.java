package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecursosHumanosController {

	@Autowired
	public JavaMailSender emailSender;

	@GetMapping("rh-catalogos")
	public String rh() {
		return "rh-catalogos";
	}

	// @GetMapping("rh-agregar-empleados")
	// public String agregarEmpleados() {
	// return "rh-agregar-empleados";
	// }

	// @GetMapping("rh-empleados")
	// public String rhempleados() {
	// return "rh-empleados";
	// }

	// @GetMapping("rh-incrementos")
	// public String rhIncremento() {
	// return "rh-incrementos";
	// }

	@GetMapping("rh-agregar-incrementos")
	public String agregarIncrementos() {
		return "rh-agregar-incrementos";
	}

	@GetMapping("rh-mail")
	public String mail() {
		SimpleMailMessage message = new SimpleMailMessage();
		// Se envia el correo con la url de confirmacions
		message.setTo("cuatepitziuriel@gmail.com");
		message.setFrom("dtu_test@uniformes-altima.com.mx");
		message.setSubject("Bienvenido a Altima: ");
		message.setText("Hola, Se ha enviado un nuevo correo, da clic al enlace para confirmar tu email: ");

		// Send Message!
		this.emailSender.send(message);
		return "redirect:/";
	}

	@GetMapping("rh-solicitudes")
	public String solicitudes() {
		return "rh-solicitudes";
	}

	@GetMapping("rh-agregar-solicitudes")
	public String agregarSolicitudes() {
		return "rh-agregar-solicitudes";
	}

	// @GetMapping("rh-permisos")
	// public String permisos() {
	// return "rh-permisos";
	// }

	// @GetMapping("rh-agregar-permisos")
	// public String agregarPermisos() {
	// return "rh-agregar-permisos";
	// }

	@GetMapping("rh-incrementos-detalle")
	public String details() {
		return "rh-incrementos-detalle";
	}
}

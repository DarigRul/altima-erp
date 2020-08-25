package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.DisenioNotificacion;
import com.altima.springboot.app.models.service.IDisenioNotificacionService;

@RestController
public class NotificacionDisenioRestController {
	@Autowired
	IDisenioNotificacionService NotificacionService;

	@PostMapping("/viewnotification")
	public boolean ViewNotification(Long id) {
		System.out.println("entraaaa");
		boolean response;

		DisenioNotificacion disenionotificacion = NotificacionService.findOne(id);
		System.out.println(disenionotificacion.getFolio());

		try {
			disenionotificacion.setEstatus(1);
			NotificacionService.save(disenionotificacion);
			response = true;
		} catch (Exception e) {
			response = false;
		}

		return response;

	}
	@GetMapping("/countnotifications")
	public Integer CountNotifications() {
		
		return NotificacionService.notificationnumber();

	}

}

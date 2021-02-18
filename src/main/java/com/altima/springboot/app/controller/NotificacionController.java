package com.altima.springboot.app.controller;

import java.util.Date;

import com.altima.springboot.app.models.entity.Notificacion;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificacionController {
    
    @MessageMapping("/mensaje")
	@SendTo("/chat/mensaje")
	public Notificacion recibeMensaje(Notificacion mensaje) {
		mensaje.setFecha(new Date().getTime());
		mensaje.setTexto("recibido ok:"+mensaje.getTexto());
        System.out.println("holi");
		return mensaje;
	}

    @GetMapping("/notificaciones")
    public String noti() {
        return "notificaciones";
    }
}

package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.service.IDisenioNotificacionService;

@Controller
public class NotificacionDisenioController {
	@Autowired
	IDisenioNotificacionService NotificacionService;

	@GetMapping("notificaciones-diseno")
	public String notiDisenio(Model model) {

		model.addAttribute("notificaciones", NotificacionService.findAllWithApplicantName());
		return "notificaciones-diseno";
	}

	@GetMapping("/notificaciones/solicitud-gerencial/{id}")
	public String listPre(Model model, @PathVariable(name = "id") Long Idsolicitud) {
		if (Idsolicitud != null) {
			ProduccionPedido formpedido = new ProduccionPedido();
			// List<ComercialClienteSucursal> listSucursales =
			// serviceSucursal.findListaSucrusales();
			List<ProduccionPedido> lispedidos = NotificacionService.findOneNotification(Idsolicitud);
			model.addAttribute("formpedido", formpedido);
			// model.addAttribute("listSucur", listSucursales);
			model.addAttribute("lispedidos", lispedidos);
		}
		return "solicitud-gerencial";
	}

}
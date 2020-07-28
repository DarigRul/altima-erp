package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.DisenioNotificacion;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IDisenioNotificacionService;
import com.altima.springboot.app.models.service.IProduccionPedidoService;

@Controller
public class NotificacionDisenioController {
	@Autowired
	IDisenioNotificacionService NotificacionService;
	@Autowired
	private IComercialClienteSucursalService serviceSucursal;
	@Autowired
	private IProduccionPedidoService servicePedido;
	
    @GetMapping("notificaciones-diseno")
    public String notiDisenio(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
        model.addAttribute("notificaciones", NotificacionService.findAllWithApplicantName());
        return "notificaciones-diseno";
    }
   
    
    @GetMapping("/notificaciones/solicitud-gerencial/{id}")
	public String listPre(Model model,@PathVariable(name = "id") Long Idsolicitud) {
		if(Idsolicitud!=null) {
    	ProduccionPedido formpedido = new ProduccionPedido();
		List<ComercialClienteSucursal> listSucursales = serviceSucursal.findListaSucrusales();
		List<ProduccionPedido> lispedidos= NotificacionService.findOneNotification(Idsolicitud);
		model.addAttribute("formpedido", formpedido);
		model.addAttribute("listSucur", listSucursales);		
		model.addAttribute("lispedidos", lispedidos);	
		}
		return "solicitud-gerencial";
	}
    
    
}
package com.altima.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IProduccionSolicitudCambioTelaPedidoService;

@Controller
public class CambioTelaController {
	@Autowired
	private IProduccionSolicitudCambioTelaPedidoService CambioTelaService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	@Autowired
    AuthComponent auth;

	
	 @GetMapping("/solicitud-cambio-tela")
	 public String SolicitudCambioTela(Model model){
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     String roles =auth.getAuthorities().toString();
	     
	     if (roles.contains("ROLE_COMERCIAL_SOLICITUD_CAMBIO_TELA_GERENCIA")||roles.contains("ROLE_ADMINISTRADOR")) {
	            model.addAttribute("view", CambioTelaService.View(0L));
	            model.addAttribute("pedidosClose", CambioTelaService.pedidosCerrados(0L));
	        } else {
	        	System.out.println(this.auth.currentemployeeid());
	            model.addAttribute("view", CambioTelaService.View(this.auth.currentemployeeid()));
	            model.addAttribute("pedidosClose", CambioTelaService.pedidosCerrados(this.auth.currentemployeeid()));
	        }
	     model.addAttribute("auth", this.auth.currentemployeeid());
	        return"solicitud-cambio-tela";
	 }
	 
	 @GetMapping("/coordinados-cambio-tela/{idPedido}/{idSolicitud}")
		public String listCoordinados(
				@PathVariable(value = "idPedido") Long idPedido,
				@PathVariable(value="idSolicitud") Long idSolicitud,
				Model model) {
			model.addAttribute("coordinados", CoordinadoService.findAllEmpresa(idPedido));
			model.addAttribute("id_pedido", idPedido);
			model.addAttribute("id_solicitud", idSolicitud);
			return "coordinados-cambio-tela";
		}
	 
	 @GetMapping("/cambio-prenda-tela/{idCoor}/{idSolicitud}")
		public String addCoordinados(@PathVariable(value = "idCoor") Long idCoor,
				@PathVariable(value="idSolicitud") Long idSolicitud,
				Map<String, Object> model, Model m) {
		 
			ComercialCoordinadoPrenda prenda = new ComercialCoordinadoPrenda();
			prenda.setIdCoordinado(idCoor);
			model.put("prenda", prenda);
			model.put("listPrendas", CoordinadoService.findAllPrenda());
			m.addAttribute("id_solicitud", idSolicitud);
			model.put("listCoorPrenda", CoordinadoService.findAllCoorPrenda(idCoor));
			return "cambio-prenda-tela";
		}

}

package com.altima.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IProduccionSolicitudCambioTelaPedidoService;

@Controller
public class CambioTelaController {
	@Autowired
	private IProduccionSolicitudCambioTelaPedidoService CambioTelaService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	 @GetMapping("/solicitud-cambio-tela")
	 public String SolicitudCambioTela(Model model){
		 	model.addAttribute("view", CambioTelaService.View());
	        return"solicitud-cambio-tela";
	 }
	 
	 @GetMapping("/coordinados-cambio-tela/{id}")
		public String listCoordinados(@PathVariable(value = "id") Long id, Model model) {
			model.addAttribute("coordinados", CoordinadoService.findAllEmpresa(id));

			model.addAttribute("id_pedido", id);
			return "coordinados-cambio-tela";
		}
	 
	 @GetMapping("/cambio-prenda-tela/{id}")
		public String addCoordinados(@PathVariable(value = "id") Long id, Map<String, Object> model) {
			ComercialCoordinadoPrenda prenda = new ComercialCoordinadoPrenda();
			prenda.setIdCoordinado(id);
			model.put("prenda", prenda);
			model.put("listPrendas", CoordinadoService.findAllPrenda());

			model.put("listCoorPrenda", CoordinadoService.findAllCoorPrenda(id));
			return "agregar-coordinado";
		}

}

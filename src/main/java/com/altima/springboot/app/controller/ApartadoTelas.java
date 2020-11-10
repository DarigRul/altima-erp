package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialAgentesVentaService;

@Controller
public class ApartadoTelas {
	
	@Autowired
	private IComercialAgentesVentaService agenteVentaService;
	
	@Autowired
	private ICargaPedidoService pedidoService;
	
	@GetMapping("/apartado-de-telas")
	public String listApartado(Model model) {
		
		model.addAttribute("ListPedidos", agenteVentaService.findAllApartadoTelas());
		
		return "apartado-de-telas";
	}
	
	@GetMapping("/editar-apartado-de-telas/{id}")
	public String editApartado(@PathVariable(value="id")Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		ComercialPedidoInformacion pedidoInfo = pedidoService.findOne(id);
		
		pedidoInfo.setFechaApartadoTelas(dateFormat.format(date));
		pedidoInfo.setActualizadoPor(auth.getName());
		
		pedidoService.save(pedidoInfo);
		
		return "redirect:/apartado-de-telas";
	}
	
	@RequestMapping(value = {"/detalle-reporte/{id}"}, method = RequestMethod.GET)
	public String detalleReporte(@PathVariable(value="id")Long id, Model model) {
		
		model.addAttribute("apartadoTelasReporte", agenteVentaService.findDatosReporteApartadoTelas(id));
		
		return "/detalle-reporte";
	}
	
	@RequestMapping(value = {"/detalle-reporte-general/{id}"}, method = RequestMethod.GET)
	public String detalleReporteGeneral(@PathVariable(value="id")Long id, Model model) {
		
		model.addAttribute("apartadoTelasReporte", agenteVentaService.findDatosReporteApartadoTelas(id));
		
		return "/detalle-reporte-general";
	}
}

package com.altima.springboot.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.MaquilaAsignacionTickets;
import com.altima.springboot.app.models.entity.MaquilaControlPedido;
import com.altima.springboot.app.models.service.IMaquilaAsignacionTicketsService;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoBultoService;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoService;

@Controller
public class TallerMaquilaControlPedidosController {

	@Autowired
	IMaquilaControlPedidoService maquilaControlPedidoService;
	@Autowired
	IMaquilaControlPedidoBultoService maquilaControlPedidoBultoService;
	@Autowired
	IMaquilaAsignacionTicketsService maquilaAsignacionTicketsService;
	
	@GetMapping("/control-pedidos")
	public String ListControlPedidos(Model model) {
		model.addAttribute("control_pedido", maquilaControlPedidoService.findAllMaquilaControlPedido());
		model.addAttribute("clientes", maquilaControlPedidoService.findAllCliente());
		model.addAttribute("prenda_modelo", maquilaControlPedidoService.findAllPrendaModelo());

		return "/control-pedidos";
	}
	
	@PostMapping("/guardar-control-pedidos")
	public String GuardarControlPedidos(Model model,RedirectAttributes redirectAttrs,String cliente,String pedido,
			String modelo,Integer cantidad,String orden,String coordinado,String clave_tela,String fecha
			) {
        String[] arrOfStr = modelo.split("\\|"); 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		MaquilaControlPedido maquilacontrol=new MaquilaControlPedido();
		maquilacontrol.setPedido(pedido);
		maquilacontrol.setActualizadoPor(auth.getName());
		maquilacontrol.setClaveTela(clave_tela);
		maquilacontrol.setCliente(cliente);
		maquilacontrol.setConfeccion(cantidad);
		maquilacontrol.setCoordinado(coordinado);
		maquilacontrol.setCreadoPor(auth.getName());
		maquilacontrol.setEstatus("1");
		maquilacontrol.setFechaCreacion(hourdateFormat.format(date));
		maquilacontrol.setFechaEntrega(hourdateFormat.format(date));
		maquilacontrol.setFechaProgramadaEntrega(hourdateFormat.format(date));
		maquilacontrol.setFechaRecepcion(hourdateFormat.format(date));
		maquilacontrol.setModelo(arrOfStr[0]);
		maquilacontrol.setOrdenProduccion(orden);
		maquilacontrol.setPrenda(arrOfStr[1]);
		maquilacontrol.setIdPrenda(Long.parseLong(arrOfStr[2]));
		maquilacontrol.setUltimaFechaModificacion(hourdateFormat.format(date));
		maquilaControlPedidoService.save(maquilacontrol);
		 redirectAttrs.addFlashAttribute("title", "Guardado correctamente").addFlashAttribute("icon",
					"success");
		return "redirect:/control-pedidos";
	}
	@PostMapping("/guardar-tickets-asignacion")
	@ResponseBody
	public Boolean GuardarTickets(String idcontrol, String idprenda) {
		Boolean response = false;
		try {
			maquilaAsignacionTicketsService.saveTickets(idcontrol,idprenda);
			Integer i=1;
			for (MaquilaAsignacionTickets iterable_element : maquilaAsignacionTicketsService.findByControlPedido(Long.parseLong(idcontrol))) {
				
				
				iterable_element.setBulto((i++).toString());

				maquilaAsignacionTicketsService.save(iterable_element);
			}
			response=true;
		} catch (Exception e) {
			// TODO: handle exception
			response=false;
		}
		

		return response;
	}
	
	
	@GetMapping("/maquilacontrolpedidostickets/{idcontrol}/{idprenda}")
	public String TicketsMaquilaControlPedidos(@PathVariable("idcontrol") Long idcontrol,@PathVariable("idprenda") Long idprenda, Model model) throws ServletException, IOException {
		
		model.addAttribute("tickets", maquilaAsignacionTicketsService.ImprimirTickets(idcontrol,idprenda));
		return "/maquilacontrolpedidostickets";
	}
}

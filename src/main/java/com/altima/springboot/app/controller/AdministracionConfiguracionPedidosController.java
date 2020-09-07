package com.altima.springboot.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.IAdminConfiguracionPedidoService;
import com.altima.springboot.app.models.service.IComercialCalendarioService;


@Controller
public class AdministracionConfiguracionPedidosController {
	@Autowired
	IAdminConfiguracionPedidoService configService;
	
	
    @GetMapping(value="/configuracion-de-pedidos")
    public String ConfiguracionPedidosList(Model model , Map<String, Object> m) {
    	model.addAttribute("pedidos", configService.findAllView());
    	AdminConfiguracionPedido config = new AdminConfiguracionPedido();
		m.put("config", config);
        return "configuracion-de-pedidos";
    }
    
    @PostMapping("/guardar-config-pedido")
	public String guardarCliente(AdminConfiguracionPedido config,  RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if ( config.getMinimoPersonas().isEmpty()) {
			config.setMinimoPersonas(null);
		}
		
		if ( config.getIdConfiguracionPedido() == null) {
			config.setCreadoPor(auth.getName());
			config.setActualizadoPor(null);
			config.setFechaCreacion(hourdateFormat.format(date));
			config.setUltimaFechaModificacion(null);
			config.setEstatus("1");
			configService.save(config);
			redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon",
					"success");
		}
		else {
			config.setActualizadoPor(auth.getName());
			config.setUltimaFechaModificacion(hourdateFormat.format(date));
			configService.save(config);
			redirectAttrs.addFlashAttribute("title", "Pedido editado correctamente").addFlashAttribute("icon",
					"success");
			
		}


		return "redirect:configuracion-de-pedidos";
	}
	
	@RequestMapping(value = "/editar-pedido", method = RequestMethod.GET)
	@ResponseBody
	public AdminConfiguracionPedido editar(Long id ) {
		AdminConfiguracionPedido config = configService.findOne(id);
		return config;
	}
	
	
	
	@RequestMapping(value = "/delete-pedido", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteMaterial(Long id) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		AdminConfiguracionPedido config = configService.findOne(id);
		config.setEstatus("0");
		config.setActualizadoPor(auth.getName());
		config.setUltimaFechaModificacion(hourdateFormat.format(date));
		configService.save(config);
		return true;
	}
	
	@RequestMapping(value = "/active-pedido", method = RequestMethod.GET)
	@ResponseBody
	public boolean activeMaterial( Long id) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		AdminConfiguracionPedido config = configService.findOne(id);
		config.setEstatus("1");
		config.setActualizadoPor(auth.getName());
		config.setUltimaFechaModificacion(hourdateFormat.format(date));
		configService.save(config);
		System.out.println("ACTIVE");
		return true;
	}
    
	@RequestMapping(value = "/validar-tipo", method = RequestMethod.GET)
	@ResponseBody
	public boolean validar(Long id , String nombre ) {
		return configService.validarPedido(id, nombre);
	}
}
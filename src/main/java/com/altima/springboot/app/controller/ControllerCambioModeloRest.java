package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComercialConcetradoPrenda;
import com.altima.springboot.app.models.entity.ComercialCotizacionPrenda;
import com.altima.springboot.app.models.entity.ComercialHistorialCambioPrenda;
import com.altima.springboot.app.models.service.IComercialConcentradoPrendasService;
import com.altima.springboot.app.models.service.IComercialHistorialCambioPrendaService;

@RestController
public class ControllerCambioModeloRest {
	
	@Autowired
	private IComercialHistorialCambioPrendaService serviceCambio;

	@Autowired
	private IComercialConcentradoPrendasService concentradoPrendasService;
	
	@RequestMapping(value="/listar-coordinado", method = RequestMethod.GET)
	public List<Object []> listarCoordinado (Long idEmpleado){
		
		return serviceCambio.coordinadoEmpleado(idEmpleado);
	}
	
	@RequestMapping(value="/listar-modelo", method = RequestMethod.GET)
	public List<Object []> listarModelo (Long idEmpleado, Long idCoor){
		
		return serviceCambio.modelo(idEmpleado, idCoor);
	}
	
	@RequestMapping(value="/listar-cambios-posibles", method = RequestMethod.GET)
	public List<Object []> listarCambiosPosibles (Long modelo, Long idPrincipal){	
		return serviceCambio.cambio(idPrincipal, modelo);
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/guardar-cambio-spf", method = RequestMethod.POST)
	@ResponseBody
	public boolean guardar(Long idConsentrado, Long cambio) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
		ComercialConcetradoPrenda aux = concentradoPrendasService.findOne(idConsentrado);
		ComercialHistorialCambioPrenda objCambio = new ComercialHistorialCambioPrenda();
		objCambio.setIdConcentradoPrenda(idConsentrado);
		objCambio.setIdPrenda(aux.getIdCoordinadoPrenda());
		objCambio.setIdText("Cambio");
		
		objCambio.setCreadoPor(auth.getName());
		objCambio.setFechaCreacion(hourdateFormat.format(date));
		objCambio.setEstatus("1");
		serviceCambio.save(objCambio);
		aux.setIdCoordinadoPrenda(cambio);
		concentradoPrendasService.save(aux);
		
	
		
		return true;
	}
}


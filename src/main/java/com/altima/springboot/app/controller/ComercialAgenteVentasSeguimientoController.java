package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComercialCotizacion;
import com.altima.springboot.app.models.entity.ComercialSolicitudModelo;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlCliente;
import com.altima.springboot.app.models.service.IComercialAgentesVentaService;
import com.altima.springboot.app.models.service.IComercialCotizacionService;
import com.altima.springboot.app.models.service.IComercialSolicitudModeloService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteService;

@RestController
public class ComercialAgenteVentasSeguimientoController {

	@Autowired
	private IComercialAgentesVentaService agentesVentaService;
	
	@Autowired
	private IComercialSolicitudModeloService solModeloService;
	
	@Autowired
	private IComercialSolicitudServicioAlClienteService servClienteService;
	
	@Autowired
	private IComercialCotizacionService cotiService;
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_SEGUIMIENTOS_LISTAR"})
	@RequestMapping(value = "/seguimientoDetalles", method = RequestMethod.GET)
	public List<Object[]> listSeguimientosDetalles(@RequestParam(value="idCliente")Long idCliente) {
		return agentesVentaService.findListasSeguimientoByidCliente(idCliente);
	}
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_SEGUIMIENTOS_LISTAR"})
	@RequestMapping(value = "/guardarObservacionesSeguimiento", method = RequestMethod.POST)
	public void guardarObservacionesSeguimiento(@RequestParam(value="idRegistro")Long idRegistro,
												@RequestParam(value="nombreTabla")String nombreTabla,
												@RequestParam(value="observaciones")String observaciones) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		if(nombreTabla.equalsIgnoreCase("solmodelo")) {
			ComercialSolicitudModelo solModelo = solModeloService.findOne(idRegistro);
			
			solModelo.setObservacionesSeguimiento(observaciones);
			solModelo.setActualizadoPor(auth.getName());
			solModelo.setUltimaFechaModificacion(dateFormat.format(date));
			
			solModeloService.save(solModelo);
		}
		
		else if(nombreTabla.equalsIgnoreCase("serviciocliente")) {
			ComercialSolicitudServicioAlCliente servCliente = servClienteService.findOne(idRegistro);
			
			servCliente.setObservacionesSeguimiento(observaciones);
			servCliente.setActualizadoPor(auth.getName());
			servCliente.setUltimaFechaModificacion(dateFormat.format(date));
			
			servClienteService.save(servCliente);
		}
		
		else if(nombreTabla.equalsIgnoreCase("cotizacion")) {
			ComercialCotizacion coti = cotiService.findOne(idRegistro);
			
			coti.setObservacionesSeguimiento(observaciones);
			coti.setActualizadoPor(auth.getName());
			
			cotiService.save(coti);
		}
	}
}

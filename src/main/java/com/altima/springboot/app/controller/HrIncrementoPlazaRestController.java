package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.altima.springboot.app.models.entity.HrIncrementoPlaza;
import com.altima.springboot.app.models.service.IHrIncrementoPlazaService;

@RestController
public class HrIncrementoPlazaRestController {

	@Autowired
	private IHrIncrementoPlazaService incrementoPlazaService;

	// Método para editar plazas
	@GetMapping("/rh-editar-incrementos")
	public Object editarIncrementos(@RequestParam(name = "id") Long id) {
		return incrementoPlazaService.editarPlazas(id);
	}

	// Método para listar departamentos por idArea
	@GetMapping("/rh-listarDepas")
	public Object listarDepas(@RequestParam(name = "area") Long id) {
		return incrementoPlazaService.listarDepartamentos(id);
	}

	// Método para listar puestos y sueldos por idDepartamento
	@GetMapping("/rh-listarPuestos")
	public Object listarPuestos(@RequestParam(name = "departamento") Long id) {
		return incrementoPlazaService.listarPuestos(id);
	}

	@PostMapping("/motivoRechazo")
	public Object motivoRechazo(@RequestParam(name = "motivo") String motivo,
			@RequestParam(name = "idIncremento") Long idIncremento) {
		try {
			Date date2 = new Date();
			Authentication authMotivos = SecurityContextHolder.getContext().getAuthentication();
			DateFormat ultimaActualizacionRechazados = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			HrIncrementoPlaza motivos = incrementoPlazaService.findOne(idIncremento);
			motivos.setMotivoRechazo(motivo);
			motivos.setEstatus("3");
			motivos.setEstatusPlaza("3");
			motivos.setFechaAutorizacion(ultimaActualizacionRechazados.format(date2));
			motivos.setUltimaFechaModificacion(ultimaActualizacionRechazados.format(date2));
			motivos.setActualizadoPor(authMotivos.getName());
			incrementoPlazaService.save(motivos);
			return "redirect:/rh-incrementos";
		} catch (Exception e) {
			System.out.println("Error:   " + e);
			return "redirect:/rh-incrementos";
		} finally {
			System.out.println("Terminó el proceso de motivos");
		}
	}
}

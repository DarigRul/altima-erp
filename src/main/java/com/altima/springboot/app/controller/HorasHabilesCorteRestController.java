package com.altima.springboot.app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.security.access.annotation.Secured;

import com.altima.springboot.app.dto.HorasHabliesListDto;
import com.altima.springboot.app.models.entity.ProduccionCalendario;
import com.altima.springboot.app.models.entity.ProduccionCalendarioProceso;
import com.altima.springboot.app.models.service.IProduccionCalendarioProcesoService;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class HorasHabilesCorteRestController {

    @Autowired
    private IProduccionCalendarioService CalendarioService;

    @Autowired
    private IProduccionCalendarioProcesoService calendarioProcesoService; 

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_LISTAR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR"})
    @RequestMapping(value = "/get_buscar_fechas_calendario", method = RequestMethod.GET)
	public List<HorasHabliesListDto> listar(String fechaInicio, String fechaFin,Long idProceso) {
        return CalendarioService.mostrar_calendario(fechaInicio, fechaFin,idProceso) ;
    }
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR"})
    @RequestMapping(value = "/get_calendario_id", method = RequestMethod.GET)
	public ProduccionCalendario buscar (Long id) {
        return CalendarioService.findOne(id);
    }  

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR"})
    @PostMapping("/postCalendarioProduccion")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> postCalendarioProduccion(Long idCalendario, float hombre, float favor,float contra, String obs,Long idProceso ) {
		Map<String, Object> response = new HashMap<>();
		ProduccionCalendarioProceso calendarioProceso=new ProduccionCalendarioProceso();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
            calendarioProceso.setHorasContra(contra);
            calendarioProceso.setHorasFavor(favor);
            calendarioProceso.setHorasHombre(hombre);
            calendarioProceso.setIdCalendarioFecha(idCalendario);
            calendarioProceso.setIdProceso(idProceso);
            calendarioProceso.setActualizadoPor(auth.getName());
            calendarioProceso.setCreadoPor(auth.getName());
			calendarioProcesoService.save(calendarioProceso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProduccionCalendarioProceso>(calendarioProceso,HttpStatus.CREATED);
	}
}

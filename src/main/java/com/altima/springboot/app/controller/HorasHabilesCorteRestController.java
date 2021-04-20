package com.altima.springboot.app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_HORAS_HABILES_CORTE_LISTAR",
            "ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR" })
    @RequestMapping(value = "/get_buscar_fechas_calendario", method = RequestMethod.GET)
    public List<HorasHabliesListDto> listar(String fechaInicio, String fechaFin, Long idProceso) {
        return CalendarioService.mostrar_calendario(fechaInicio, fechaFin, idProceso);
    }

    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR" })
    @RequestMapping(value = "/get_calendario_id", method = RequestMethod.GET)
    public ProduccionCalendario buscar(Long id) {
        return CalendarioService.findOne(id);
    }

    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR" })
    @PostMapping("/calendarioProduccion")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postCalendarioProduccion(Long idCalendarioFecha, float hombre, float favor, float contra,
            Long idProceso, String comentarios) {
        Map<String, Object> response = new HashMap<>();
        ProduccionCalendarioProceso calendarioProceso = new ProduccionCalendarioProceso();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            calendarioProceso.setHorasContra(contra);
            calendarioProceso.setHorasFavor(favor);
            calendarioProceso.setHorasHombre(hombre);
            calendarioProceso.setIdCalendarioFecha(idCalendarioFecha);
            calendarioProceso.setIdProceso(idProceso);
            calendarioProceso.setActualizadoPor(auth.getName());
            calendarioProceso.setCreadoPor(auth.getName());
            calendarioProceso.setComentarios(comentarios);
            calendarioProcesoService.save(calendarioProceso);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProduccionCalendarioProceso>(calendarioProceso, HttpStatus.CREATED);
    }
    
    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR" })
    @GetMapping("calendarioProduccion/{id}")
	public ResponseEntity<?> getCliente(@PathVariable(name="id") Long id) {

		
		Map<String, Object> response = new HashMap<>();
		ProduccionCalendarioProceso calendarioProceso = null;
		try {
			calendarioProceso =calendarioProcesoService.findOne(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(calendarioProceso == null){
			response.put("mensaje", "La empresa con el id "+ id +" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProduccionCalendarioProceso>(calendarioProceso,HttpStatus.OK);
	}
    
    @Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR" })
    @PutMapping("calendarioProduccion/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> putEmpresa(@RequestBody ProduccionCalendarioProceso updateCalendarioProceso,@PathVariable(name="id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		float horasContra=updateCalendarioProceso.getHorasContra();
        float horasFavor=updateCalendarioProceso.getHorasFavor();
        float horasHombre=updateCalendarioProceso.getHorasHombre();
        String comentarios=updateCalendarioProceso.getComentarios();
		Map<String, Object> response = new HashMap<>();
        ProduccionCalendarioProceso calendarioProceso=calendarioProcesoService.findOne(id);
		if(calendarioProceso==null){
			response.put("mensaje", "El registro con el id "+ id +" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			calendarioProceso.setHorasContra(horasContra);
            calendarioProceso.setHorasFavor(horasFavor);
            calendarioProceso.setHorasHombre(horasHombre);
            calendarioProceso.setComentarios(comentarios);
            calendarioProceso.setActualizadoPor(auth.getName());
			calendarioProcesoService.save(calendarioProceso);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProduccionCalendarioProceso>(calendarioProceso,HttpStatus.CREATED);
	}
}

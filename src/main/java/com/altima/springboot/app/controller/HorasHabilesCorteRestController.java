package com.altima.springboot.app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.security.access.annotation.Secured;
import com.altima.springboot.app.models.entity.ProduccionCalendario;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class HorasHabilesCorteRestController {

    @Autowired
    private IProduccionCalendarioService CalendarioService;

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_LISTAR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR"})
    @RequestMapping(value = "/get_buscar_fechas_calendario", method = RequestMethod.GET)
	public List<Object []> listar(String fechaInicio, String fehaFin) {
        return CalendarioService.mostrar_calendario(fechaInicio, fehaFin) ;
    }
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR"})
    @RequestMapping(value = "/get_calendario_id", method = RequestMethod.GET)
	public ProduccionCalendario buscar (Long id) {
        return CalendarioService.findOne(id);
    }
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_HORAS_HABILES_CORTE_EDITAR"})
    @RequestMapping(value = "/guardar_calendario_produccion", method = RequestMethod.GET)
	public String guardar (Long idCalendario, String hombre, String adeudo, String obs ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ProduccionCalendario obj = CalendarioService.findOne(idCalendario);
        obj.setHombre(hombre);
        obj.setAdeudo(adeudo);
        obj.setObservacion(obs);
        obj.setActualizadoPor(auth.getName());
        obj.setUltimaFechaModificacion(hourdateFormat.format(date));
        CalendarioService.save(obj);
        return CalendarioService.restarHoras(hombre, adeudo);
	}
    
}

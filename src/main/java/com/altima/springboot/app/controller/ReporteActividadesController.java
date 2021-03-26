package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import com.altima.springboot.app.models.entity.ServicioClienteObservacionProceso;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IServicioClienteLookupService;
import com.altima.springboot.app.models.service.IServicioClienteObservacionProcesoService;
import com.altima.springboot.app.models.service.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class ReporteActividadesController {
    
    @Autowired
	private IServicioClienteLookupService servicioClienteService;

    @Autowired
    private IServicioClienteObservacionProcesoService observacionService;

    @Autowired
	private IUsuarioService usuarioService;
    
    @GetMapping(value="/reporte-actividades")
    public String view(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
        if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("usuarios", observacionService.fullUser());
		} 

        model.addAttribute("procesos", servicioClienteService.findAllLookup("Proceso"));
        model.addAttribute("procesostable", observacionService.view(iduser));
        return "reporte-actividades";
    }
    
	@RequestMapping(value="/guardar_reporte_actividades", method=RequestMethod.GET)
	@ResponseBody
	public boolean save(Long idProceso, String observacion, Long idObservacionProceso){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
        Formatter fmt = new Formatter();
        Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
        if( idObservacionProceso == null){
            ServicioClienteObservacionProceso obj = new ServicioClienteObservacionProceso ();
            obj.setIdProceso(String.valueOf(idProceso));
            obj.setObservacion(observacion);
            obj.setCreadoPor(auth.getName());
            obj.setFechaCreacion(dateFormat.format(date));
            obj.setIdUsuario(String.valueOf(iduser));
            observacionService.save(obj);
            obj.setIdText("PROC"+(obj.getIdObservacionProceso()+1000));
            observacionService.save(obj);
            return true;
        }
		else{
            ServicioClienteObservacionProceso editar = observacionService.findOne(idObservacionProceso);
            editar.setObservacion(observacion);
            editar.setActualizadoPor(auth.getName());
            editar.setUltimaFechaModificacion(dateFormat.format(date));
            observacionService.save(editar);
            return true;
        }
	}

    @RequestMapping(value="/obtener_observacion_by_id", method=RequestMethod.GET)
	@ResponseBody
	public ServicioClienteObservacionProceso get( Long idObservacionProceso){
        return observacionService.findOne(idObservacionProceso);

	}

    @RequestMapping(value="/validar_duplicado_observacion", method=RequestMethod.GET)
	@ResponseBody
	public boolean get(Long idProceso , String observacion, Long idObservacion){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
        if (idObservacion ==  null ){
            return  observacionService.validarDuplicadoIdIsNull(iduser, idProceso, observacion);
        }
        else{
            return observacionService.validarDuplicadoIdIsNotNul(iduser, idProceso, observacion, idObservacion);
        }
        

	}

    
	@RequestMapping(value="/asignar_reporte_actividades", method=RequestMethod.GET)
	@ResponseBody
	public boolean asginar(Long idObservacionProceso, String idUsuario){
        ServicioClienteObservacionProceso editar = observacionService.findOne(idObservacionProceso);
        editar.setIdUsuario(idUsuario);
        observacionService.save(editar);
        return true;
	}
}

package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.service.IProduccionPermisoUsuarioProcesoService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.models.entity.ProduccionMaquilador;
import com.altima.springboot.app.models.entity.ProduccionPermisoUsuarioProceso;

@RestController
public class ProduccionPermisoUsuarioProcesoRestContoller {

    @Autowired
    private IProduccionPermisoUsuarioProcesoService permisoService;

    @RequestMapping(value = "/listar_usuarios_disponibles_by_proceso", method = RequestMethod.GET)
	public List<Object []> SelectUsuariosDisponibles(Long idProceso) {
       
        return permisoService.SelectUsuariosDisponibles(idProceso);
    }

    @RequestMapping(value = "/listar_usuarios_asignados_by_proceso", method = RequestMethod.GET)
	public List<Object []> view(Long idProceso) {
       
        return permisoService.view(idProceso);
    }


    @RequestMapping(value = "/guardar_usuario_proceso", method = RequestMethod.GET)
	public boolean save(Integer idProceso, Integer idUsuario ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ProduccionPermisoUsuarioProceso permisoObject = new ProduccionPermisoUsuarioProceso();
        permisoObject.setIdProceso(idProceso);
        permisoObject.setIdUsuario(idUsuario);
        permisoObject.setCreadoPor(auth.getName());
        permisoObject.setFechaCreacion(dateFormat.format(date));
        permisoService.save(permisoObject);
        return true;
    }

    @RequestMapping(value = "/eliminar_usuario_proceso", method = RequestMethod.GET)
	public boolean save(Long idPermiso) {
        permisoService.delete(idPermiso);
        return true;
    }
    
}

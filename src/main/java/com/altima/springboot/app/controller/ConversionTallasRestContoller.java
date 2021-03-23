package com.altima.springboot.app.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteConversionTallas;
import com.altima.springboot.app.models.service.IServicioClienteConversionTallasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionTallasRestContoller {

    @Autowired
    private IServicioClienteConversionTallasService serviceConsesion;

    
    @RequestMapping(value="/mostrar_pedido_estatus_3_conversion", method=RequestMethod.GET)
	public List<Object []> mostrarTelas (){
		return serviceConsesion.listarPedidosEstatus3();
	}
    
    @RequestMapping(value="/devolver_datos_del_pedido_by_id", method=RequestMethod.GET)
    public List<Object []> devolver (@RequestParam(name="id") Long id){
		return serviceConsesion.devolverDatos(id);
	}

    @RequestMapping(value="/devolver_datos_del_pedido_by_id_editar", method=RequestMethod.GET)
    public List<Object []> devolver_editar (@RequestParam(name="id") Long id){
		return serviceConsesion.devolverEditar(id);
	}

    @RequestMapping(value="/guardar_conversion_tallas", method=RequestMethod.GET)
    public boolean save (
        @RequestParam(name="fechaRecepcion") String fechaRecepcion,
        @RequestParam(name="fechaEntrega") String fechaEntrega,
        @RequestParam(name="conversiones") String conversiones,
        @RequestParam(name="porcentaje") String porcentaje,
        @RequestParam(name="observaciones") String observaciones,
        @RequestParam(name="idPedido") String idPedido,
        @RequestParam(name="idConversionTallas") Long idConversionTallas ){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    Date date = new Date();
		    DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            if ( idConversionTallas == null){
                ServicioClienteConversionTallas obj = new ServicioClienteConversionTallas();
                obj.setIdPedido(idPedido);
                obj.setFechaRecepcion(fechaRecepcion);
                obj.setFechaEntrega(fechaEntrega);
                obj.setCantidadConsersion(conversiones);
                obj.setObservacion(observaciones);
                obj.setInsidencia("0");
                obj.setEstatus("1");
                obj.setPorcentaje(porcentaje);
                obj.setCreadoPor(auth.getName());
                obj.setFechaCreacion(hourdateFormat.format(date));
                serviceConsesion.save(obj);
            }
            else{
                ServicioClienteConversionTallas edi = serviceConsesion.findOne(idConversionTallas);
                edi.setFechaRecepcion(fechaRecepcion);
                edi.setFechaEntrega(fechaEntrega);
                edi.setCantidadConsersion(conversiones);
                edi.setObservacion(observaciones);
                edi.setPorcentaje(porcentaje);
                edi.setActualizadoPor(auth.getName());
                edi.setUltimaFechaModificacion(hourdateFormat.format(date));
                serviceConsesion.save(edi);
            }
            

        return true;
    }
    //marcar_incidencia_by_id
    @RequestMapping(value="/marcar_incidencia_by_id", method=RequestMethod.GET)
    public String marcarDesmarcarIncidencia (@RequestParam(name="id") Long id, @RequestParam(name="accion") String accion ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if( accion.equals("1") ){
            ServicioClienteConversionTallas edi = serviceConsesion.findOne(id);
            edi.setInsidencia("1");
            edi.setActualizadoPor(auth.getName());
                edi.setUltimaFechaModificacion(hourdateFormat.format(date));
            serviceConsesion.save(edi);
            return "marcado";
        }
		else if(accion.equals("0") ){
            ServicioClienteConversionTallas edi = serviceConsesion.findOne(id);
            edi.setInsidencia("0");
            edi.setActualizadoPor(auth.getName());
                edi.setUltimaFechaModificacion(hourdateFormat.format(date));
            serviceConsesion.save(edi);
            return "desmarcado";
        }
        return "0";
	}
}

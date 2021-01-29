package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.altima.springboot.app.models.entity.MaquilaComponenteInventario;
import com.altima.springboot.app.models.entity.MaquilaInventarioActivoFijo;
import com.altima.springboot.app.models.service.IMaquilaInventarioActivoFijoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaquilaInventarioActivoFijoRestController {

    @Autowired
    private IMaquilaInventarioActivoFijoService inventarioService;

    /*
    $('#tipo').val() == "" || 
            $('#').val() == "" || 
            $('#').val() == "" ||
            $('#').val() == "" ||
            $('#').val() == "" ||
            $('#motor').val() == ""
    */
    @RequestMapping(value = "/guardar_inventario_maquila_fijo", method = RequestMethod.GET)
	public boolean listar(Long idInventario, String tipo,String activoFijo, String modelo, String marca, String serie , String motor) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       if ( idInventario == null){
            MaquilaInventarioActivoFijo obj = new MaquilaInventarioActivoFijo();
            obj.setIdLookup(tipo);
            obj.setActivoFijo(activoFijo);
            obj.setModelo(modelo);
            obj.setMarca(marca);
            obj.setSerie(serie);
            obj.setMotor(motor);
            obj.setCreadoPor(auth.getName());
            obj.setFechaCreacion(hourdateFormat.format(date));
            obj.setFechaIngresa(hourdateFormat.format(date));
            obj.setEstatus("1");
            inventarioService.save(obj);       
            obj.setClave(""+obj.getIdInventario());  
            inventarioService.save(obj); 

        return true;
       }
       else{
            MaquilaInventarioActivoFijo editar = inventarioService.findOne(idInventario);
            editar.setIdLookup(tipo);
            editar.setActivoFijo(activoFijo);
            editar.setModelo(modelo);
            editar.setMarca(marca);
            editar.setSerie(serie);
            editar.setMotor(motor);
            editar.setActualizadoPor(auth.getName());
            editar.setUltimaFechaModificacion(hourdateFormat.format(date));
            inventarioService.save(editar); 

           return true;
       }
    }

    @RequestMapping(value = "/buscar_inventario_maquila_fijo", method = RequestMethod.GET)
	public MaquilaInventarioActivoFijo buscar(Long idInventario) {
        return inventarioService.findOne(idInventario);
    }

    @RequestMapping(value = "/estatus_inventario_maquila_fijo", method = RequestMethod.GET)
	public boolean estatus(Long idInventario, String estatus) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        MaquilaInventarioActivoFijo es = inventarioService.findOne(idInventario);
        es.setEstatus(estatus);
        if ( estatus.equals("1")){
            es.setFechaBajo(null);
        }else{
            es.setFechaBajo(hourdateFormat.format(date));
        }
        es.setActualizadoPor(auth.getName());
        es.setUltimaFechaModificacion(hourdateFormat.format(date));

        inventarioService.save(es); 
        return true;
    }

    @RequestMapping(value = "/listar_componentes_by_id_inventario", method = RequestMethod.GET)
	public List<Object[]> componentes(Long idInventario) {
        return inventarioService.viewComponentes(idInventario);
    }

    @RequestMapping(value = "/guardar_componente_inventario", method = RequestMethod.GET)
	public boolean guardarCompontes(String idComponentes, String idInventario , String marca, String cantidad) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      
            MaquilaComponenteInventario obj = new MaquilaComponenteInventario();
            obj.setIdLookup(idComponentes);
            obj.setIdInventario(idInventario);
            obj.setMarca(marca);
            obj.setCantidad(cantidad);
            obj.setCreadoPor(auth.getName());
            obj.setFechaCreacion(hourdateFormat.format(date));
            inventarioService.saveComponente(obj); 
        

        return true;
       
    }

    @RequestMapping(value = "/eliminar_componente_inventario", method = RequestMethod.GET)
	public boolean guardarCompontes(Long id) {
      
        inventarioService.deleteComponentes(id); 
            


        return true;
       
    }
    
}

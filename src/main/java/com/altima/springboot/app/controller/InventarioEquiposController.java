package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.altima.springboot.app.models.entity.SoporteTecnicoEquipoMantenimiento;
import com.altima.springboot.app.models.entity.SoporteTecnicoInventario;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.ISoporteTecnicoInventarioService;
import com.altima.springboot.app.models.service.ISoporteTecnicoLookupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InventarioEquiposController {

    @Autowired
    private ISoporteTecnicoLookupService lookupService;
    @Autowired
    private IHrEmpleadoService empleadoService;

    @Autowired
    private ISoporteTecnicoInventarioService inventarioService;

    @GetMapping("/inventario-equipos")
    public String view (Model model){
        model.addAttribute("tipos_equipos", lookupService.findAllByType("Equipo"));
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("vista", inventarioService.view());
        return "inventario-equipos";
    }

    @RequestMapping(value = "/guardar_inventario_soporte_tecnico", method = RequestMethod.POST)
	@ResponseBody
	public boolean cambioEstatus(Long idInventario, String tipo, String proveedor ,
        String fecha, String marca,String modelo,String serie,
        String procesador,String ram,String discoDuro,
        String pantalla,String nsPantalla,String direccionIp,String asignadoA,String observaciones) {
            
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		if (idInventario == null ){
            SoporteTecnicoInventario obj = new SoporteTecnicoInventario();
            obj.setProvedor(proveedor);
            obj.setTipo(tipo);
            obj.setFecha(fecha);
            obj.setMarca(marca);
            obj.setModelo(modelo);
            obj.setSerie(serie);
            obj.setProcesador(procesador);
            obj.setRam(ram);
            obj.setDiscoDuro(discoDuro);
            obj.setPantalla(pantalla);
            obj.setNsPantalla(nsPantalla);
            obj.setDireccion_ip(direccionIp);
            obj.setAsignadoA(asignadoA);
            obj.setCreadoPor(auth.getName());
            obj.setFechaCreacion(dateFormat.format(date));
            obj.setEstatus("1");
            obj.setObservaciones(observaciones);
            inventarioService.save(obj);
            return true;
        }else{

            SoporteTecnicoInventario obj2 = inventarioService.findOne(idInventario);
            obj2.setProvedor(proveedor);
            obj2.setFecha(fecha);
            obj2.setMarca(marca);
            obj2.setModelo(modelo);
            obj2.setSerie(serie);
            obj2.setProcesador(procesador);
            obj2.setTipo(tipo);
            obj2.setRam(ram);
            obj2.setDiscoDuro(discoDuro);
            obj2.setPantalla(pantalla);
            obj2.setNsPantalla(nsPantalla);
            obj2.setDireccion_ip(direccionIp);
            obj2.setAsignadoA(asignadoA);
            obj2.setObservaciones(observaciones);
            obj2.setActualizadoPor(auth.getName());
            obj2.setUltimaFechaModificacion(dateFormat.format(date));
            inventarioService.save(obj2);
            return true;
        }
    }
    
    @RequestMapping(value = "/buscar_id_inventaio_soporte", method = RequestMethod.POST)
    @ResponseBody
    public SoporteTecnicoInventario buscar (Long id){
        return inventarioService.findOne(id);
    }

    @RequestMapping(value = "/cambiar_estatus_inventario_soporte", method = RequestMethod.POST)
    @ResponseBody
    public boolean buscar (Long id, String estatus){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
        SoporteTecnicoInventario obj2 = inventarioService.findOne(id);
        obj2.setEstatus(estatus);
        obj2.setActualizadoPor(auth.getName());
        obj2.setUltimaFechaModificacion(dateFormat.format(date));
        inventarioService.save(obj2);
        return true;
    }

    @RequestMapping(value = "/guardar_mantenimieto", method = RequestMethod.POST)
    @ResponseBody
    public boolean buscar (String idEquipo,String fecha,
    String tipo, String motivo, String fechaCompra, String proveedor, String NS , 
    String descripcion,String fechaProxima, String actividad ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
        SoporteTecnicoEquipoMantenimiento obj = new SoporteTecnicoEquipoMantenimiento();
        obj.setIdEquipo(idEquipo);
        obj.setFecha(fecha);
        obj.setTipo(tipo);
        obj.setMotivo(motivo);
        obj.setFechaCompra(fechaCompra);
        obj.setProveedor(proveedor);
        obj.setNS(NS);
        obj.setDescripcion(descripcion);
        obj.setFechaProxima(fechaProxima);
        obj.setActividad(actividad);
        obj.setCreadoPor(auth.getName());
        obj.setFechaCreacion(dateFormat.format(date));
        inventarioService.save(obj);
        return true;
    }

    
}

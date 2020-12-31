package com.altima.springboot.app.controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlCliente;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteAuxiliarVentas;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteCorrida;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteMaterial;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteSastre;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteAuxiliarVentasService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteCorridaService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteMaterialService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteSastreService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteService;
import com.altima.springboot.app.models.service.IHrDireccionService;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;

@RestController
public class ProduccionCalendarioRestController {

    @Autowired
    private IProduccionCalendarioService CalendarioService;
    
    @RequestMapping(value = "/get_validar_calendario", method = RequestMethod.GET)
	public boolean getPedidosDeCliente() {
       
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy");
      
        if (CalendarioService.validarAnio(hourdateFormat.format(date)) ==0){
            return false;
        }else{
            return true;
        }
    }
    
    @RequestMapping(value = "/get_crear_calendario", method = RequestMethod.GET)
	public boolean crear() {
        System.out.println("dddddddddddd");
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy");
        CalendarioService.crearCalendario(hourdateFormat.format(date)+"-"+"01-01", hourdateFormat.format(date)+"-"+"12-31");
        return true;
	}

    
}

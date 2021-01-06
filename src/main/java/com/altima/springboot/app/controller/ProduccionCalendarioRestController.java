package com.altima.springboot.app.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy");
        DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        CalendarioService.crearCalendario(hourdateFormat.format(date)+"-"+"01-01", hourdateFormat.format(date)+"-"+"12-31",auth.getName(),hourdateFormat2.format(date));
        return true;
	}

    
}

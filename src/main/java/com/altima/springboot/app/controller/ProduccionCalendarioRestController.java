package com.altima.springboot.app.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class ProduccionCalendarioRestController {

    @Autowired
    private IProduccionCalendarioService CalendarioService;
    
    @RequestMapping(value = "/get_validar_calendario", method = RequestMethod.GET)
	public boolean getPedidosDeCliente(@RequestParam String year) {
             
        if (CalendarioService.validarAnio(year) ==0){
            return false;
        }else{
            return true;
        }
    }
    
    @PostMapping(value = "/post_crear_calendario")
	public boolean crear(@RequestParam String year) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy");
        DateFormat hourdateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        CalendarioService.crearCalendario(year+"-"+"01-01", year+"-"+"12-31",auth.getName(),hourdateFormat2.format(date));
        return true;
	}

    
}

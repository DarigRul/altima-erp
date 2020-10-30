package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.altima.springboot.app.models.service.IAmpRequisicionAlmacenService;
import java.util.List;
@Controller
public class AlmacenRequisicionController {
	
	@Autowired
	private IAmpRequisicionAlmacenService ServiceAlmacen;
	
    @GetMapping("/requisicion-de-almacen")
    public String RequisicionAlmacenList(){
        return"requisicion-de-almacen";
    }
    @GetMapping("/requisicion-de-almacen-nueva")
    public String RequisicionAlmacenAdd(Map<String, Object> model, Model m){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if ( auth.getName().equals("ADMIN") ) {
			System.out.println("Soy el ADMIN");
			model.put("solicitante", "ADMIN");
			model.put("departamento", "ADMIN");
		}else {
			Object[] lista = ServiceAlmacen.infoUsuario(auth.getName());
			model.put("solicitante", lista[0]);
			model.put("departamento", lista[1]);
		}
		
	
		model.put("fecha", hourdateFormat.format(date));
		model.put("folio", null);
		m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
        return"requisicion-de-almacen-nueva";
    }
}
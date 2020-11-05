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
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.AmpRequisicionAlmacen;
import com.altima.springboot.app.models.service.IAmpRequisicionAlmacenService;

@Controller
public class ComprasAlmacenRequisicionController {
	
	@Autowired
	private IAmpRequisicionAlmacenService ServiceAlmacen;
	
	@Autowired
    AuthComponent auth;

	
	 @GetMapping("/requisicion-de-compras/{id}")
	    public String editar(Map<String, Object> model, Model m,@PathVariable(value = "id") Long id){
	    	
	    
			AmpRequisicionAlmacen obj = ServiceAlmacen.findOne(id);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
			if ( auth.getName().equals("ADMIN") ) {
				System.out.println("Soy el ADMIN");
				model.put("solicitante", "ADMIN");
				model.put("departamento", "ADMIN");
			}else {
				Object[] lista = ServiceAlmacen.infoUsuario(this.auth.currentuserid());
				model.put("solicitante", lista[0]);
				model.put("departamento", lista[1]);
			}
			
		
			model.put("fecha", hourdateFormat.format(date));
			model.put("folio", null);
			model.put("idRequisicion", null);
			m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
	  
		
			model.put("compras", true);
			m.addAttribute("tableMateriales", ServiceAlmacen.viewMaterial(id));
	        return"requisicion-de-almacen-nueva";
	    }
}

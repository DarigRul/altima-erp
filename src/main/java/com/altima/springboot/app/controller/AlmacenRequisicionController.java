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
public class AlmacenRequisicionController {
	
	@Autowired
	private IAmpRequisicionAlmacenService ServiceAlmacen;
	
	@Autowired
    AuthComponent auth;
	
    @GetMapping("/requisicion-de-almacen")
    public String RequisicionAlmacenList(Model model){
    	
    	
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     String roles =auth.getAuthorities().toString();
	     
	     if (roles.contains("ROLE_COMERCIAL_SOLICITUD_CAMBIO_TELA_GERENCIA")||roles.contains("ROLE_ADMINISTRADOR")) {
	            model.addAttribute("view", ServiceAlmacen.view(0L));
	        } else {
	            model.addAttribute("view", ServiceAlmacen.view(this.auth.currentemployeeid()));
	        }
	    
	     
        return"requisicion-de-almacen";
    }
    @GetMapping("/requisicion-de-almacen-nueva")
    public String RequisicionAlmacenAdd(Map<String, Object> model, Model m){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if ( auth.getName().equals("ADMIN") ) {
			m.addAttribute("listSolicitante", ServiceAlmacen.viewListEmpleado());
		}else {
			Object[] lista = ServiceAlmacen.infoUsuario(this.auth.currentuserid());
			model.put("solicitante", lista[0]);
			model.put("departamento", lista[1]);
		}
		
	
		model.put("fecha", hourdateFormat.format(date));
		model.put("folio", null);
		model.put("idRequisicion", null);
		
		model.put("almacen", true);
		m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
        return"requisicion-de-almacen-nueva";
    }
    
    @GetMapping("/requisicion-de-almacen-editar/{id}")
    public String editar(Map<String, Object> model, Model m,@PathVariable(value = "id") Long id){
    	
    
		AmpRequisicionAlmacen obj = ServiceAlmacen.findOne(id);
		System.out.println(id);
		
		if ( obj.getIdSolicitante() == 1) {
			model.put("solicitante", "ADMIN");
			model.put("departamento", "ADMIN");
		}
		
		else {
			Object[] lista = ServiceAlmacen.infoUsuario(obj.getIdSolicitante());
			model.put("solicitante", lista[0]);
			model.put("departamento", lista[1]);
		}
		
	
		model.put("fecha", obj.getFechaCreacion());
		model.put("folio", obj.getIdText());
		model.put("idRequisicion", obj.getIdRequsicionAlmacen());
		m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
		model.put("almacen", true);
		m.addAttribute("tableMateriales", ServiceAlmacen.viewMaterial(id));
        return"requisicion-de-almacen-nueva";
    }
}
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
import com.altima.springboot.app.models.entity.ComprasRequisicionAlmacen;
import com.altima.springboot.app.models.service.IAmpRequisicionAlmacenService;
import com.altima.springboot.app.models.service.IComprasRequisicionAlmacenService;

@Controller
public class ComprasAlmacenRequisicionController {

	@Autowired
	private IAmpRequisicionAlmacenService ServiceAlmacen;

	@Autowired
	private IComprasRequisicionAlmacenService ServiceCompras;

	@Autowired
	AuthComponent auth;

	@GetMapping("/requisicion-de-compras")
	public String RequisicionComprasList(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String roles = auth.getAuthorities().toString();

		if (roles.contains("ROLE_ADMINISTRADOR")) {
			model.addAttribute("view", ServiceCompras.view(0L));
		} else {
			model.addAttribute("view", ServiceCompras.view(this.auth.currentemployeeid()));
		}
		return "requisicion-de-compras";
	}

	@GetMapping("/requisicion-de-compras/{id}")
	public String editar(Map<String, Object> model, Model m, @PathVariable(value = "id") Long id) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		AmpRequisicionAlmacen obj = ServiceAlmacen.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals("ADMIN")) {
			m.addAttribute("listSolicitante", ServiceAlmacen.viewListEmpleado());
			model.put("idSelect", obj.getIdSolicitante());
		} else {
			Object[] lista = ServiceAlmacen.infoUsuario(this.auth.currentuserid());
			model.put("solicitante", lista[0]);
			model.put("departamento", lista[1]);
			model.put("noAdmin", true);
			model.put("idEmpleadoSolicitante", this.auth.currentuserid());

		}

		model.put("fecha", hourdateFormat.format(date));
		model.put("folio", null);
		model.put("idRequisicion", null);
		model.put("idSolicitudAlamcen", id);

		// model.put("fecha", obj.getFechaCreacion());
		// model.put("folio", obj.getIdText());
		// model.put("idRequisicion", obj.getIdRequsicionAlmacen());
		m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
		model.put("compras", true);
		m.addAttribute("tableMateriales", ServiceAlmacen.viewMaterial(id));
		// return"requisicion-de-almacen-nueva";
		return "requisicion-de-compras-nueva";
	}

	@GetMapping("/requisicion-de-compras-nueva")
	public String RequisicionAlmacenAdd(Map<String, Object> model, Model m) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (auth.getName().equals("ADMIN")) {
			m.addAttribute("listSolicitante", ServiceAlmacen.viewListEmpleado());
		} else {
			Object[] lista = ServiceAlmacen.infoUsuario(this.auth.currentuserid());
			model.put("solicitante", lista[0]);
			model.put("departamento", lista[1]);
			model.put("noAdmin", true);
			model.put("idEmpleadoSolicitante", this.auth.currentuserid());

		}

		model.put("fecha", hourdateFormat.format(date));
		model.put("folio", null);
		model.put("idRequisicion", null);

		model.put("compras", true);
		m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
		return "requisicion-de-compras-nueva";
	}
	
	@GetMapping("/requisicion-de-compras-editar/{id}")
    public String editar2(Map<String, Object> model, Model m,@PathVariable(value = "id") Long id){
		ComprasRequisicionAlmacen obj = ServiceCompras.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if ( auth.getName().equals("ADMIN") ) {
			m.addAttribute("listSolicitante", ServiceAlmacen.viewListEmpleado());
			model.put("idSelect",obj.getIdSolicitante());
		}else {
			Object[] lista = ServiceAlmacen.infoUsuario(this.auth.currentuserid());
			model.put("solicitante", lista[0]);
			model.put("departamento", lista[1]);
			model.put("noAdmin", true);
			model.put("idEmpleadoSolicitante",this.auth.currentuserid());
			
		}
		
	
		model.put("fecha", obj.getFechaCreacion());
		model.put("folio", obj.getIdText());
		model.put("idRequisicion", obj.getIdRequsicionAlmacen());
		m.addAttribute("materiales", ServiceAlmacen.AllMateriales());
		model.put("compras", true);
		m.addAttribute("tableMateriales", ServiceCompras.viewMaterial(id));
        return"requisicion-de-compras-nueva";
	}
}

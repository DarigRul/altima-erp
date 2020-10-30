package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.AmpRequisicionAlmacen;
import com.altima.springboot.app.models.entity.AmpRequisicionAlmacenMaterial;
import com.altima.springboot.app.models.entity.DisenioComposicionIcuidado;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.service.IAmpRequisicionAlmacenService;

@RestController
public class AlmacenRequisicionRestController {
	@Autowired
	private IAmpRequisicionAlmacenService ServiceAlmacen;
	@Autowired
    AuthComponent auth;

	
	@SuppressWarnings("null")
	@RequestMapping(value = "/guardar-requisicion-materiales", method = RequestMethod.POST)
	@ResponseBody
	public String guardar(@RequestParam(name = "datos") String datos, Long idRequisicion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if (idRequisicion == null) {
			AmpRequisicionAlmacen  requi = new AmpRequisicionAlmacen ();
			if ( auth.getName().equals("ADMIN")) {
				requi.setIdSolicitante(1L);
			}else {
				requi.setIdSolicitante(this.auth.currentuserid());
			}
			
			requi.setIdText("REQ");
			requi.setCreadoPor(auth.getName());
			requi.setActualizadoPor(auth.getName());
			requi.setFechaCreacion(hourdateFormat.format(date));
			requi.setUltimaFechaModificacion(hourdateFormat.format(date));
			requi.setEstatusEnvio("0");
			ServiceAlmacen.save(requi);
			requi.setIdText("REQ"+ (requi.getIdRequsicionAlmacen()+1000000));
		
			JSONArray json = new JSONArray(datos);
			for (int i = 0; i < json.length(); i++) {
				AmpRequisicionAlmacenMaterial material = new AmpRequisicionAlmacenMaterial();
				JSONObject object = (JSONObject) json.get(i);
				String id = object.get("id_material").toString();
				String tipo = object.get("tipo").toString();
				String cantidad = object.get("cantidad").toString();
				
				material.setIdRequisicionAlmacen(requi.getIdRequsicionAlmacen());
				material.setIdMaterial(Long.valueOf(id));
				material.setIdText("Mar");
				material.setCantidad(Float.parseFloat(cantidad));
				material.setTipoMaterial(tipo);
				material.setCreadoPor(auth.getName());
				material.setActualizadoPor(auth.getName());
				material.setFechaCreacion(hourdateFormat.format(date));
				material.setUltimaFechaModificacion(hourdateFormat.format(date));
				material.setEstatus("1");
				ServiceAlmacen.save(material);
				
			}
		} else {

			System.out.println(idRequisicion);
			AmpRequisicionAlmacen  requi = ServiceAlmacen.findOne(idRequisicion);
			
			JSONArray json = new JSONArray(datos);
			for (int i = 0; i < json.length(); i++) {
				AmpRequisicionAlmacenMaterial material = new AmpRequisicionAlmacenMaterial();
				JSONObject object = (JSONObject) json.get(i);
				String id = object.get("id_material").toString();
				String tipo = object.get("tipo").toString();
				String cantidad = object.get("cantidad").toString();
				
				if ( ServiceAlmacen.findOne(id, tipo, cantidad, idRequisicion) == null) {
					material.setIdRequisicionAlmacen(requi.getIdRequsicionAlmacen());
					material.setIdMaterial(Long.valueOf(id));
					material.setIdText("Mar");
					material.setCantidad(Float.parseFloat(cantidad));
					material.setTipoMaterial(tipo);
					material.setCreadoPor(auth.getName());
					material.setActualizadoPor(auth.getName());
					material.setFechaCreacion(hourdateFormat.format(date));
					material.setUltimaFechaModificacion(hourdateFormat.format(date));
					material.setEstatus("1");
					ServiceAlmacen.save(material);
				}
				
				
			}
		}
		return "Es correcto";
	}
	
	@GetMapping("/elimiar-requisicion-materiales")
	@ResponseBody
	public void eliminarcomposicioncuidado(Long idRequision) {
		ServiceAlmacen.deleteRequisicionMaterial(idRequision);
	}

}

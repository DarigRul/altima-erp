package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.ComprasRequisicionAlmacen;
import com.altima.springboot.app.models.entity.ComprasRequisicionAlmacenMaterial;
import com.altima.springboot.app.models.service.IComprasRequisicionAlmacenService;


@RestController
public class ComprasAlmacenRequisicionRestController {
	
	@Autowired
	private IComprasRequisicionAlmacenService ServiceCompras;
	@Autowired
    AuthComponent auth;

	
	@SuppressWarnings("null")
	@RequestMapping(value = "/guardar-requisicion-compras", method = RequestMethod.POST)
	@ResponseBody
	public String guardar(@RequestParam(name = "datos") String datos, Long idRequisicion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if (idRequisicion == null) {
			ComprasRequisicionAlmacen  requi = new ComprasRequisicionAlmacen ();
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
			ServiceCompras.save(requi);
			requi.setIdText("REQ"+ (requi.getIdRequsicionAlmacen()+1000000));
		
			JSONArray json = new JSONArray(datos);
			for (int i = 0; i < json.length(); i++) {
				ComprasRequisicionAlmacenMaterial material = new ComprasRequisicionAlmacenMaterial();
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
				ServiceCompras.save(material);
				
			}
		} else {

			ComprasRequisicionAlmacen  requi = ServiceCompras.findOne(idRequisicion);
			
			JSONArray json = new JSONArray(datos);
			for (int i = 0; i < json.length(); i++) {
				ComprasRequisicionAlmacenMaterial material = new ComprasRequisicionAlmacenMaterial();
				JSONObject object = (JSONObject) json.get(i);
				String id = object.get("id_material").toString();
				String tipo = object.get("tipo").toString();
				String cantidad = object.get("cantidad").toString();
				
				if ( ServiceCompras.findOne(id, tipo, cantidad, idRequisicion) == null) {
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
					ServiceCompras.save(material);
				}
				
				
			}
		}
		return "Es correcto";
	}

}

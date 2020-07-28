package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComercialConcetradoPrenda;
import com.altima.springboot.app.models.service.IComercialConcentradoPrendasService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;

import antlr.collections.List;

@RestController
public class ConcentradoPrendasRestController 
{
	@Autowired
	private IComercialConcentradoPrendasService concentradoPrendasService;
	
	@Autowired
	private IComercialCoordinadoService coordinadoService;
	
	@RequestMapping(value = "/get_coordinados", method = RequestMethod.GET)
	public Object get_coordinados(@RequestParam Long id) {
		return concentradoPrendasService.findCoordinadosfromPedido(id);
	}
	
	@RequestMapping(value = "/get_prendas_de_coordinado", method = RequestMethod.GET)
	public Object detalleMaterial(@RequestParam Long id) {
		return concentradoPrendasService.findMaterialPrendaTelafromCoordinado(id);
	}
	
	@RequestMapping(value = "/get_numero_de_coordinado", method = RequestMethod.GET)
	public Object getNumeroCoordinado(@RequestParam Long id) {
		return coordinadoService.findOne(id);
	}
	
	@RequestMapping(value = "/get_empleados", method = RequestMethod.GET)
	public Object getEmpleados(@RequestParam Long id) {
		return concentradoPrendasService.findAllEmpleadosByPedido(id);
	}
	
	@RequestMapping(value = "/get_cantidades_prendas_de_coordinado", method = RequestMethod.GET)
	public Object cantidadesPrendasCoordinados(@RequestParam Long id, @RequestParam Long idPedido) {
		
		Object[] obj = new Object[2];
		obj[0] = concentradoPrendasService.findMaterialPrendaTelafromCoordinado(id);
		obj[1] = concentradoPrendasService.findCantidadesPrendasfromCoordinado(id, idPedido);
		
		return obj;
	}
	
	@RequestMapping(value = "/guardar_prendas_empleado", method = RequestMethod.GET)
	public String guardarPrendasEmpleado(@RequestParam(name = "prendas_empleado") String prendas_empleado,
			@RequestParam(name = "idCoordinado") Long idCoordinado,
			@RequestParam(name = "idEmpleado") Long idEmpleado) throws NoSuchFieldException, SecurityException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		
		JSONArray prendasEmpleadoArray = new JSONArray(prendas_empleado);
		String idModelos[] = new String[prendasEmpleadoArray.length()];
		for (int i = 0; i < prendasEmpleadoArray.length(); i++) {
			JSONObject prendasEmpleado = prendasEmpleadoArray.getJSONObject(i);
			ComercialConcetradoPrenda cp = new ComercialConcetradoPrenda();			
			cp.setIdEmpleado(idEmpleado);
			cp.setIdCoordinadoPrenda(Long.valueOf(prendasEmpleado.get("modelo").toString()));
			cp.setCantidad(prendasEmpleado.get("cantidad").toString());
			cp.setCantidadEspecial(prendasEmpleado.get("cantidadSp").toString());
			cp.setCreado_por(auth.getName());
			cp.setActualizadoPor(auth.getName());
			cp.setFechaCreacion(dtf.format(now));
			cp.setUltimaFechaModificacion(dtf.format(now));
			cp.setEstatus("1");
			concentradoPrendasService.save(cp);
			idModelos[i] = prendasEmpleado.get("modelo").toString();
		}
		System.out.println(idEmpleado);
		concentradoPrendasService.delete(idModelos, idEmpleado, idCoordinado);
		

		return "ok";
	}
	
	@GetMapping("/get_prendas_de_empleado_editar")
	public Object[] prendasDeEmpleado(@RequestParam Long idEmpleado, @RequestParam Long idCoordinado) {
		
		Object[] obj = new Object[2];
		
		obj[0] = concentradoPrendasService.findPrendasFromEmpleado(idEmpleado, idCoordinado);
		obj[1] = concentradoPrendasService.findMaterialPrendaTelafromCoordinado(idCoordinado);
		
		return obj;
		
	}
}

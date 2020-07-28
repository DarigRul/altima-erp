package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioRuta;
import com.altima.springboot.app.models.entity.DisenioRutaProceso;
import com.altima.springboot.app.models.service.DisenioRutaProcesoServiceImpl;
import com.altima.springboot.app.models.service.DisenioRutaServiceImpl;
import com.altima.springboot.app.models.service.IDisenioLookupService;

@RestController
public class RutasRestController {
	
	@Autowired
	private DisenioRutaServiceImpl disenioruta;
	@Autowired
	private IDisenioLookupService disenioLookup;
	@Autowired
	private DisenioRutaProcesoServiceImpl disenioRutaProceso;
	public Long id;
	
	@RequestMapping(value = "/crear_ruta", method = RequestMethod.POST)
	public DisenioRuta crear(@RequestParam(name = "nombre") String nombreRuta,
			@RequestParam(name = "descripcion") String descripcionRuta, @RequestParam(name = "procesos") String procesos) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioRuta rutas = new DisenioRuta();
		DisenioRutaProceso procesoRuta = new DisenioRutaProceso();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		LocalDate localDate = LocalDate.now();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = localDate + " " + dateFormat.format(date);
		if (id == null) {
			rutas.setNombreRuta(nombreRuta);
			rutas.setDescripcionRuta(descripcionRuta);
			rutas.setActualizadoPor(auth.getName());
			rutas.setCreadoPor(auth.getName());
			rutas.setFechaCreacion(formattedDate);
			rutas.setUltimaFechaModificacion(formattedDate);
			rutas.setIdText("c");
			rutas.setEstatus(1);
			disenioruta.save(rutas);
			rutas.setIdText("RUT0" + (rutas.getIdRuta() + 100000));
			disenioruta.save(rutas);
			
			String[] palabras = procesos.split(",");
			for (String a : palabras) {
				procesoRuta = new DisenioRutaProceso();
				procesoRuta.setIdRuta(rutas.getIdRuta().toString());
				procesoRuta.setIdProceso(a);
				
				disenioRutaProceso.save(procesoRuta);
			}
			DisenioRuta listarutas = disenioruta.findOne(rutas.getIdRuta());
			id = null;
			return listarutas;
		} else {
			DisenioRuta ruta = disenioruta.findOne(id);
			ruta.setNombreRuta(nombreRuta);
			ruta.setDescripcionRuta(descripcionRuta);
			disenioruta.save(ruta);
			String[] palabras = procesos.split(",");
			List<Object[]> listaprocesos = disenioRutaProceso.findByRutaEntity(id);
			
			for (Object[] i : listaprocesos) {
				disenioRutaProceso.delete(Long.parseLong(i[0].toString()));
			}
			
			for (String a : palabras) {
				procesoRuta = new DisenioRutaProceso();
				procesoRuta.setIdRuta(id.toString());
				procesoRuta.setIdProceso(a);
				disenioRutaProceso.save(procesoRuta);
			}
			
			id = null;
			return ruta;
		}
	}
	
	@RequestMapping(value = "/listarRutas", method = RequestMethod.GET)
	public List<DisenioRuta> listarRutas() {
		List<DisenioRuta> listarutas = disenioruta.findAll();
		return listarutas;
	}
	
	@RequestMapping(value = "/listarProcesos", method = RequestMethod.POST)
	public List<DisenioLookup> listarProcesos() {
		List<DisenioLookup> listaProcesos = disenioLookup.findByTipoLookup("Proceso");
		return listaProcesos;
	}
	
	@RequestMapping(value = "/editarRuta", method = RequestMethod.POST)
	public List<Object> editarRuta(@RequestParam(name = "idRuta") Long idruta) {
		id = idruta;
		List<Object> ruta = new ArrayList<>();
		DisenioRuta rut = disenioruta.findOne(idruta);
		ruta.add(rut);
		ruta.addAll(disenioRutaProceso.findByRuta(idruta));
		return ruta;
	}
	
	@RequestMapping("/borrarRegistro")
	public void borrarCache() {
		id = null;
	}
}
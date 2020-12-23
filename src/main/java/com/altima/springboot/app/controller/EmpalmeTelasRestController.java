package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IEmpalmeTelasService;

@RestController
public class EmpalmeTelasRestController {
	
	@Autowired
    private IEmpalmeTelasService EmpalmeService;
	
	@Autowired
    private IComercialCoordinadoService coorService;
	
	
	@RequestMapping(value="/guardar_ruta_a_coor_prenda", method=RequestMethod.POST)
	public boolean ruta (@RequestParam(name = "ids") String[] ids, Integer idRuta){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < ids.length; i++) {
			ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(Long.parseLong(ids[i]));
			obj.setId_ruta(idRuta);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			coorService.saveCoorPrenda(obj);	
		}
		return true;
	}
	
	@RequestMapping(value="/guardar_programa_a_coor_prenda", method=RequestMethod.POST)
	public boolean programa (@RequestParam(name = "ids") String[] ids, String programa){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < ids.length; i++) {
			ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(Long.parseLong(ids[i]));
			obj.setPrograma(programa);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			coorService.saveCoorPrenda(obj);	
		}
		return true;
	}
	
	@RequestMapping(value="/guardar_folio_a_coor_prenda", method=RequestMethod.POST)
	public boolean folio (@RequestParam(name = "ids") String[] ids, String folio){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < ids.length; i++) {
			ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(Long.parseLong(ids[i]));
			obj.setFolio(folio);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			coorService.saveCoorPrenda(obj);	
		}
		return true;
	}
	
	@RequestMapping(value="/empalme_telas_detalles", method=RequestMethod.GET)
	public List<Object []> detalles (@RequestParam(name = "id") Long id){
		return EmpalmeService.detallesTelas(id) ;
	}

}

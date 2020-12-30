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
import com.altima.springboot.app.models.service.ITiempoCorteService;
import org.springframework.security.access.annotation.Secured;
@RestController
public class TiempoCorteRestController {
    @Autowired
    private ITiempoCorteService TiempoService;
	
	@Autowired
    private IComercialCoordinadoService coorService;

    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_TIEMPO_CORTE_AÑADIR_TIEMPO"})
    @RequestMapping(value="/listar_coordinado_prenda_por_folio", method=RequestMethod.GET)
	public List<Object []> detalles (@RequestParam(name = "folio") String folio ){
		return TiempoService.detallesFolio(folio);
    }
    
    @Secured({"ROLE_ADMINISTRADOR","ROLE_PRODUCCION_TIEMPO_CORTE_AÑADIR_TIEMPO"})
    @RequestMapping(value="/add_tiempo_coordinado_prenda", method=RequestMethod.GET)
	public boolean add (@RequestParam(name = "id") Long id, @RequestParam(name = "tiempo") String tiempo  ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(id);
        obj.setTiempo(tiempo);
        obj.setActualizadoPor(auth.getName());
        obj.setUltimaFechaModificacion(hourdateFormat.format(date));
        coorService.saveCoorPrenda(obj);
		return true;
	}
    
}

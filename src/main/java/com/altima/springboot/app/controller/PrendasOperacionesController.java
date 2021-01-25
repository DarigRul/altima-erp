package com.altima.springboot.app.controller;

import static java.lang.Long.parseLong;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.altima.springboot.app.models.entity.MaquilaPrendaOperacion;
import com.altima.springboot.app.models.service.IMaquilaLookupService;
import com.altima.springboot.app.models.service.IMaquilaPrendaOperacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@CrossOrigin(origins = { "*" })
@Controller
public class PrendasOperacionesController {

    @Autowired
    IMaquilaLookupService maquilaService;

    @Autowired
    private IMaquilaPrendaOperacionService operacionService;

    @GetMapping("/asignar_operaciones_prenda/{id}")
	public String listClothes(Model model, Map<String, Object> m, @PathVariable(value = "id") Long id ) throws InterruptedException {
		//model.addAttribute("prendas", disenioPrendaService.findAllMin());
        
        model.addAttribute("selectFamilia", maquilaService.findAll("Familia", "1"));
        model.addAttribute("view", operacionService.view(id));
        model.addAttribute("idPrenda", id);
		return "asignar-operacion";
    }
    
    @RequestMapping(value = "/listar_operaciones_by_famlia_prenda", method = RequestMethod.GET)
    @ResponseBody
	public List<Object []> maquileros(Long familia, Long prenda) {
 
        return operacionService.SelectOperacion(familia, prenda);
    }

    @RequestMapping(value = "/guardar_operaciones_by_prenda", method = RequestMethod.GET)
    @ResponseBody
	public List<Object []> save(String idoperacion, String prenda) {
 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        MaquilaPrendaOperacion obj = new MaquilaPrendaOperacion();
        obj.setIdOperacion(idoperacion);
        obj.setIdPrenda(prenda);
        obj.setCreadoPor(auth.getName());
        obj.setFechaCreacion(hourdateFormat.format(date));
        operacionService.save(obj);

        return operacionService.view(Long.parseLong(prenda));
    }

    @RequestMapping(value = "/eliminar_operaciones_by_prenda", method = RequestMethod.GET)
    @ResponseBody
	public boolean save(Long idAsignacion) {
 
        
        operacionService.delete(idAsignacion);

        return true;
    }
    
}

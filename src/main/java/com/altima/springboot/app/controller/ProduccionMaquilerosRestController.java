package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;
import com.altima.springboot.app.models.service.IProduccionLookupService;
import com.altima.springboot.app.models.service.IProduccionMaquiladorService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.models.entity.ProduccionMaquilador;

@RestController
public class ProduccionMaquilerosRestController {
    @Autowired
    private IProduccionLookupService produccionService;

    @Autowired
    private IProduccionMaquiladorService maquileroService;

    @RequestMapping(value = "/listar_ubucaciones_activas", method = RequestMethod.GET)
	public List<ProduccionLookup> ubicaciones() {
       
        return produccionService.findAllLookup("Ubicación", "1");
    }

    @RequestMapping(value = "/listar_maquileros", method = RequestMethod.GET)
	public List<Object []> maquileros() {
       
        return maquileroService.findAllCompleto();
    }
    @RequestMapping(value = "/editar_maquilero", method = RequestMethod.GET)
	public ProduccionMaquilador editar(Long idMaquilero) {
       
        return maquileroService.findOne(idMaquilero);
    }

    

    @RequestMapping(value = "/guardar_maquilero", method = RequestMethod.GET)
	public boolean save(String nombreMaquilero,Long idMaquilero, String calleMaquilero, String numeroExtMaquilero, String numeroIntMaquilero,
                                        String estadoMaquilero, String municipioMaquilero, String coloniaMaquilero, String cpMaquilero, String idUbicacion,
                                        String produccionMax, String tipo , String  descripcion,String telefono ) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        if ( idMaquilero == null){
            ProduccionMaquilador obj = new ProduccionMaquilador();
            Formatter fmt = new Formatter();
            obj.setNombre(nombreMaquilero);
            obj.setCalle(calleMaquilero);
            obj.setNumeroExt(numeroExtMaquilero);
            obj.setNumeroInt(numeroIntMaquilero);
            obj.setEstado(estadoMaquilero);
            obj.setMunicipio(municipioMaquilero);
            obj.setColonia(coloniaMaquilero);
            obj.setCodigoPostal(cpMaquilero);
            obj.setIdUbicacion(idUbicacion);
            obj.setProduccionMaxima(produccionMax);
            obj.setTipo(tipo);
            obj.setDescripcion(descripcion);
            obj.setTelefono(telefono);
            obj.setCreadoPor(auth.getName());
            obj.setFechaCreacion(dateFormat.format(date));
            maquileroService.save(obj);
            obj.setIdText("MAQUI"+ fmt.format("%04d",(obj.getIdMaquilador())));
            obj.setEstado("1");
            maquileroService.save(obj);

            return true;
        }else{

            ProduccionMaquilador obj2 = maquileroService.findOne(idMaquilero);
            obj2.setNombre(nombreMaquilero);
            obj2.setCalle(calleMaquilero);
            obj2.setNumeroExt(numeroExtMaquilero);
            obj2.setNumeroInt(numeroIntMaquilero);
            obj2.setEstado(estadoMaquilero);
            obj2.setMunicipio(municipioMaquilero);
            obj2.setColonia(coloniaMaquilero);
            obj2.setCodigoPostal(cpMaquilero);
            obj2.setIdUbicacion(idUbicacion);
            obj2.setProduccionMaxima(produccionMax);
            obj2.setTipo(tipo);
            obj2.setTelefono(telefono);
            obj2.setDescripcion(descripcion);
            obj2.setCreadoPor(auth.getName());
            obj2.setFechaCreacion(dateFormat.format(date));
            maquileroService.save(obj2);
            return true;
        }
        
    }

    
	@PostMapping("/baja-catalogo-produccion-maquilero")
	public String bajamaquilero(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ProduccionMaquilador lookupMaqui = null;
		lookupMaqui = maquileroService.findOne(id);
		lookupMaqui.setEstatus("0");
		lookupMaqui.setUltimaFechaModificacion(dateFormat.format(date));
		lookupMaqui.setActualizadoPor(auth.getName());
		maquileroService.save(lookupMaqui);
		return "redirect:catalogos";

	}
    
    
	@PostMapping("/reactivar-catalogo-produccion-maquilero")
	public String reactivarmaquilero(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ProduccionMaquilador lookupMaqui= null;
		lookupMaqui = maquileroService.findOne(idcatalogo);
		lookupMaqui.setEstatus("1");
		lookupMaqui.setUltimaFechaModificacion(dateFormat.format(date));
		lookupMaqui.setActualizadoPor(auth.getName());
		maquileroService.save(lookupMaqui);
		return lookupMaqui.getActualizadoPor();

	}
}

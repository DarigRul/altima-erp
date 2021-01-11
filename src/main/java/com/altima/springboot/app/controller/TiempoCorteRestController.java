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
import com.altima.springboot.app.models.entity.ProduccionFechaCoordinadoPrenda;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IEmpalmeTelasService;
import com.altima.springboot.app.models.service.IProduccionCalendarioService;
import com.altima.springboot.app.models.service.ITiempoCorteService;
import org.springframework.security.access.annotation.Secured;
@RestController
public class TiempoCorteRestController {
    @Autowired
    private ITiempoCorteService TiempoService;
	
	@Autowired
    private IComercialCoordinadoService coorService;

    @Autowired
    private IProduccionCalendarioService CalendadioService;

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
    
    @RequestMapping(value="/listar_fechas_disponibles_por_folio", method=RequestMethod.GET)
	public List<Object []> fechas (@RequestParam(name = "folio") String folio ){
		return CalendadioService.mostrar_fechas_disponibles_folio(folio);
    }

    @RequestMapping(value="/validar_fecha_produccion_calendario", method=RequestMethod.GET)
	public Integer fechas (@RequestParam(name = "folio") String folio, @RequestParam(name = "fecha") String fecha ){
        
        if ( TiempoService.validarExistenciaHorasHombre(fecha) ==0){
            return 0;//significa que la fecha no cuenta con horas hombre
        }
        else if ( TiempoService.validarHorasHabiles(fecha, folio) ==0){
            return 1; //Significa que la horas del folio supern la hora dispoible
        }
        else{
            return 2; // todo bien , todo correcto, y yo que me alego
        }

    }

    @RequestMapping(value="/guardar_fecha_por_folio", method=RequestMethod.GET)
	public boolean guardar (@RequestParam(name = "folio") String folio, @RequestParam(name = "Fecha") String Fecha ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<Object []> detalles = TiempoService.detallesFolio(folio);
        String idFecha = TiempoService.recuperarIdPorFecha(Fecha);

        for(int i=0;i<detalles.size();i++) {
            System.out.println(detalles.get(i)[0].toString());
            System.out.println(idFecha);
            ProduccionFechaCoordinadoPrenda obj= TiempoService.findOneFechaCoorPrenda(Integer.parseInt(detalles.get(i)[0].toString()));
            if (obj != null ){
                obj.setId_coordinado_prenda(detalles.get(i)[0].toString());
                obj.setId_fecha(idFecha);
                obj.setActualizadoPor(auth.getName());
                obj.setUltimaFechaActualizacion(hourdateFormat.format(date));
                TiempoService.save(obj);
               

            }
            
            else{
                ProduccionFechaCoordinadoPrenda objnew= new ProduccionFechaCoordinadoPrenda();
                objnew.setId_coordinado_prenda(detalles.get(i)[0].toString());
                objnew.setId_fecha(idFecha);
                objnew.setCreadoPor(auth.getName());
                objnew.setFechaCreacion(hourdateFormat.format(date));
                TiempoService.save(objnew);
            }


  
        }

        return true;
    }

    @RequestMapping(value="/listar_fechas_calendario", method=RequestMethod.GET)
	public List<Object []> calendario (@RequestParam(name = "fecha1") String fecha1,@RequestParam(name = "fecha2") String fecha2){
		return TiempoService.detallesCalendario(fecha1, fecha2);
    }
    
}

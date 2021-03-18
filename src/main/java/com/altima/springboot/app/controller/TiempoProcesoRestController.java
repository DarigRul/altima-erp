package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.entity.ProduccionFechaExplosionProceso;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;
import com.altima.springboot.app.models.service.ITiempoCorteService;
import com.altima.springboot.app.models.service.ITiempoProcesoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiempoProcesoRestController {
    
    @Autowired
    private ITiempoProcesoService TiempoService;
    
    @Autowired
    private ITiempoCorteService TiempoCorteService;

    @Autowired
	private IProduccionExplosionProcesosService explosionService;


    @RequestMapping(value="/listar_tiempo_proceso_secuencial", method=RequestMethod.GET)
	public List<Object []> listar (Long idProceso, @RequestParam String programa){
		return TiempoService.view(idProceso,programa);
    }

    @RequestMapping(value="/listar_detalles_tiempo_proceso_secuencial", method=RequestMethod.GET)
	public List<Object []> listarDetales (Long idProceso,String secuencia){
		return TiempoService.viewDetalles(idProceso, secuencia);
    }

    @RequestMapping(value="/add_tiempo_explosion_secuencial", method=RequestMethod.GET)
	public boolean add (@RequestParam(name = "id") Long id, @RequestParam(name = "tiempo") String tiempo  ){
    
        ProduccionExplosionProcesos obj = explosionService.findOne(id);
        if ( tiempo != null){
            obj.setTiempoProceso(tiempo);
        }
        explosionService.save(obj);	
		return true;
    }

    @RequestMapping(value="/validar_fecha_produccion_calendario_proceso", method=RequestMethod.GET)
	public Integer fechas (@RequestParam(name = "secuencia") String secuencia, @RequestParam(name = "fecha") String fecha, @RequestParam(name = "idProceso") Integer idProceso ){
        
        if ( TiempoCorteService.validarExistenciaHorasHombre(fecha) ==0){
            return 0;//significa que la fecha no cuenta con horas hombre
        }
        else if ( TiempoService.validarHorasHabiles(fecha, secuencia,idProceso ) ==1){
            return 1; //Significa que la horas del folio supern la hora dispoible
        }
        else{
            return 2; // todo bien , todo correcto, y yo que me alego
        }

    }

    
    @RequestMapping(value="/guardar_fecha_por_secuencia", method=RequestMethod.GET)
	public boolean guardar (@RequestParam(name = "secuencia") String secuencia, @RequestParam(name = "fecha") String Fecha, @RequestParam(name = "idProceso") Long idProceso ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<Object []> detalles = TiempoService.viewDetalles(idProceso, secuencia);
        String idFecha = TiempoCorteService.recuperarIdPorFecha(Fecha);

        for(int i=0;i<detalles.size();i++) {
            ProduccionExplosionProcesos objExplosin = explosionService.findOne(Long.valueOf(Integer.parseInt(detalles.get(i)[0].toString())));

            objExplosin.setFechaProceso(Fecha);
            explosionService.save(objExplosin);	
           /* System.out.println(detalles.get(i)[0].toString());
            System.out.println(idFecha);
            ProduccionFechaExplosionProceso   obj= TiempoService.findOneFechaCoorPrenda(Integer.parseInt(detalles.get(i)[0].toString()));
            if (obj != null ){
                obj.setId_explosion_proceso(detalles.get(i)[0].toString());
                obj.setId_fecha(idFecha);
                obj.setActualizadoPor(auth.getName());
                obj.setUltimaFechaActualizacion(hourdateFormat.format(date));
                TiempoService.save(obj);
               

            }
            
            else{
                ProduccionFechaExplosionProceso objnew= new ProduccionFechaExplosionProceso();
                objnew.setId_explosion_proceso(detalles.get(i)[0].toString());
                objnew.setId_fecha(idFecha);
                objnew.setCreadoPor(auth.getName());
                objnew.setFechaCreacion(hourdateFormat.format(date));
                TiempoService.save(objnew);
            }*/


  
        }

        return true;
    }


	@PutMapping("/tiempos-de-procesos/postFechaGeneral/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> putEmpresa(@RequestParam String tiempoGeneral,@PathVariable(name="id") Long id) {
		
		Map<String, Object> response = new HashMap<>();
		ProduccionExplosionProcesos updateExplosion=explosionService.findOne(id);
		if(updateExplosion==null){
			response.put("mensaje", "La Explosión con el id "+ id +" no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		try {
			updateExplosion.setTiempoGeneral(tiempoGeneral);
			explosionService.save(updateExplosion);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ProduccionExplosionProcesos>(updateExplosion,HttpStatus.CREATED);
	}

}

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

import com.altima.springboot.app.models.entity.ProduccionExplosionPrendas;
import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;

@RestController
public class ProduccionControlAvancesRestController {
	
	@Autowired
	IProduccionExplosionProcesosService explosionService;

	@RequestMapping(value="/listarExplosion", method = RequestMethod.GET)
	public List<ProduccionExplosionProcesos> listarExplosion (@RequestParam(name="idProceso")Long idProceso){
		
		return explosionService.listExplosionByProceso(idProceso);
	}

	@RequestMapping(value="/listar_select_realizo", method = RequestMethod.GET)
	public List<Object []> listarEmpleados (@RequestParam(value="idProceso")Long idProceso, @RequestParam(value="tipoProceso")String tipoProceso){
		if ( tipoProceso.equals("Interno")){
			return explosionService.listarEmpleadosbyProduccion();
		}
		else if (tipoProceso.equals("Externo")){
			return explosionService.listarMaquilerosbyProceso(idProceso);
		}
		else{
			return null;
		}
		
	}
	
	@RequestMapping(value="/explosionarPrendas", method = RequestMethod.GET)
	public List<Object[]> explosionarPrendas(@RequestParam(value="idExplosion")Long idExplosion, @RequestParam(value="tipo")String tipo){
		
		
		List<Object[]> listaPrendasExplosionar = explosionService.prendasExplosionarByProceso(idExplosion);
		ProduccionExplosionProcesos explosion = explosionService.findOne(idExplosion);
		if(explosion.getEstatus().equals("0")) {
			
			explosion.setEstatus("1");
			explosionService.save(explosion);
			
			for (Object[] i : listaPrendasExplosionar) {
				ProduccionExplosionPrendas explosionPrenda = new ProduccionExplosionPrendas();
				explosionPrenda.setTalla(i[5].toString());
				explosionPrenda.setIdExplosionProceso(idExplosion);
				explosionPrenda.setIdText("");
				explosionService.saveExplosionPrendas(explosionPrenda);
				explosionPrenda.setIdText("EXPREN"+(1000000+explosionPrenda.getIdExplosionPrenda()));
				explosionService.saveExplosionPrendas(explosionPrenda);
			}
		
			return explosionService.listarPrendasByExplosionProceso(idExplosion,tipo);
		}
		else {
			System.out.println("ya está explosionado");
			return explosionService.listarPrendasByExplosionProceso(idExplosion,tipo);
		}
	}
	@RequestMapping(value="/guardar_realizo_produccion_prendas", method=RequestMethod.GET)
	public Long folio (@RequestParam(name = "ids") String[] ids, String fechainicio, String fechafin,String realizo, String ubicacion){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Long data = null;
		for (int i = 0; i < ids.length; i++) {
			System.out.print(ids[i]);
			ProduccionExplosionPrendas obj = explosionService.findOnePrendas(Long.parseLong(ids[i]));
			obj.setRealizo(realizo);
			obj.setFechaInicio(fechainicio);
			obj.setFechaFin(fechafin);
			obj.setUbicacion(ubicacion);
			explosionService.saveExplosionPrendas(obj);
			data=obj.getIdExplosionProceso();
		}
		return data;
	}
}

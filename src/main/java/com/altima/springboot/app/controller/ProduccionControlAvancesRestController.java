package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/explosionarPrendas", method = RequestMethod.GET)
	public List<ProduccionExplosionPrendas> explosionarPrendas(@RequestParam(value="idExplosion")Long idExplosion){
		
		
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
		
			return explosionService.listarPrendasByExplosionProceso(idExplosion);
		}
		else {
			System.out.println("ya está explosionado");
			return explosionService.listarPrendasByExplosionProceso(idExplosion);
		}
	}
}

package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	
}

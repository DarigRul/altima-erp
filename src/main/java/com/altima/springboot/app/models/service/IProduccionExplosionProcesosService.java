package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;

public interface IProduccionExplosionProcesosService {

	void save (ProduccionExplosionProcesos produccionExplosionProcesos);
	
	ProduccionExplosionProcesos findOne (Long id);

	List<Object[]> findAllByPrograma(String programa);

	List<ProduccionExplosionProcesos> findProgramas();
	
}

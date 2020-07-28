package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioRutaProceso;

public interface IDisenioRutaProcesoService {

public List<DisenioRutaProceso> findAll();
	
	public List<DisenioRutaProceso> findById(Long id);
	
	public DisenioRutaProceso findOne(Long id);
	
	void save(DisenioRutaProceso lookup);
	
	void delete(Long id);
	
	List<Object> findByRuta(Long id);
	
	List<Object[]> findByRutaEntity(Long id);
}

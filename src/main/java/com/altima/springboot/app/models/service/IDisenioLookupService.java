package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.DisenioLookup;


public interface IDisenioLookupService {
	
	public List<DisenioLookup> findAll();
	
	public List<DisenioLookup> findByTipoLookup(String tipo);
	
	void save(DisenioLookup lookup);
	
	void delete(Long id);
	
	DisenioLookup findOne(Long id);
	

}

package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioForro;

public interface IDisenioForroService {

	List<DisenioForro> findAll();

	void save(DisenioForro disenioforro);

	void delete(Long id);

	DisenioForro findOne(Long id);
	
	List<Object []> ForrosSelect(Long id);
	
	List<Object []> ForroUnidadMedida(Long id);
	
	List<DisenioForro> forrosAutorizados();
	
	public String EstatusCalidadForro(Long id);
	
	public String buscar_forro(String nombre);

}

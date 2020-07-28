package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrIncrementoPlaza;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.models.entity.HrPuesto;

public interface IHrIncrementoPlazaService {
	
	List<HrIncrementoPlaza> findAll();

	void save(HrIncrementoPlaza incremento);

	void delete(Long id);

	HrIncrementoPlaza findOne(Long id);
	
	public List <Object []> incrementosPlazas (); 
	
	Object editarPlazas(Long id);
	
	Object listarDepartamentos(Long id);
	
	Object listarPuestos(Long id);
	
	Object findByIdIncrementoPlaza(Long id);
	
	List<HrPuesto> findAllPuestos();
	
	List<HrLookup> findAllEmpresas();
	
	List<HrDepartamento> findAllDepartamentos();
	
	
	
	
	
}

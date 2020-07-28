package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.HrPermiso;

public interface IHrPermisoService {

	List<HrPermiso> findAll();

	void save(HrPermiso hrpermiso);

	void delete(Long id);

	
	//metodos Do
	
	List<HrPermiso> findListaPermiso();
	
	HrPermiso findOne(Long id);
	
	List<HrEmpleado> findEmpleados(Long id);
	
	Object listarDepartamentos(Long id);

	Object listarPuestos(Long id);
	
	Long area(Long id);
	
	Long departamento (Long id);
	
	Long puesto (Long id);

}

package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialSpfEmpleado;

public interface IComercialSpfEmpleadoService {
	

	 List<ComercialSpfEmpleado> findAll() ;
	
	void save(ComercialSpfEmpleado ComercialSpfEmpleado);
	
	void delete(Long id);
	
	ComercialSpfEmpleado findOne(Long id);
	
	List<Object[]> empleados (Long id);

}

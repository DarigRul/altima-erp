package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialMovimiento;

public interface IComercialMovimientoService {
	
	void save (ComercialMovimiento movimientos);
	
	List<Object> listarMuestras ();
	
	public Object EncontrarMuestra(Long id);
	
	List<Object> findAllWithNames ();
	
	ComercialMovimiento findOne(Long id);

	List<Object> listarMuestrasTraspaso(Long id);

	List<Object> datosMovimiento(Long id);

	List<Object> findAllHistorico();

}

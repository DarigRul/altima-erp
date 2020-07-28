package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialCotizacion;

public interface IComercialCotizacionService {

	void save(ComercialCotizacion Comercialcotizacion);
	
	List<ComercialCotizacion> findAll ();
	
	List<Object> findAllWithTotal();
	
	ComercialCotizacion findOne(Long id);
	
	String findMax();
}

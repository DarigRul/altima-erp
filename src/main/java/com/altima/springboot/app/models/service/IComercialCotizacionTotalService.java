package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialCotizacionTotal;

public interface IComercialCotizacionTotalService {

	List<ComercialCotizacionTotal> findAll (Long id);
	
	void save (ComercialCotizacionTotal comercialCotizacionTotal);
	
	ComercialCotizacionTotal findOne (Long id);
	
	ComercialCotizacionTotal findByCotizacion(Long id);
	
}

package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialMovimientoMuestraDetalle;
import com.altima.springboot.app.models.entity.ComercialRackPrenda;

public interface IComercialRackPrendaService {

	void save(ComercialRackPrenda comercialRackPrenda);
	
	List<ComercialRackPrenda> findAllById (Long id);

	ComercialRackPrenda findOne(Long id);

	List<ComercialRackPrenda> findAllbyMovimientoEstatus(Long id);

	List<Object> datosMovimiento(Long id);

}

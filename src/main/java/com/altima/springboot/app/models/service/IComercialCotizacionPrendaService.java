package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialCotizacionPrenda;

public interface IComercialCotizacionPrendaService {

	void save(ComercialCotizacionPrenda comercialCotizacionPrenda);
	
	ComercialCotizacionPrenda findOne (Long id);
	
	List<ComercialCotizacionPrenda> findAll (Long id);
	
	Object[] FindDatosCotizacionPrenda (Long idTela, Long idPrenda);
	
	List<Object[]> FindCotizacionPrendas(Long id);
	
	double findSubtotalCotizacionPrendas(Long id);
	
	void removePrendas (List<ComercialCotizacionPrenda> comercialCotizacionPrenda);
}

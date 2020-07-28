package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialTotalRazonSocial;
import com.altima.springboot.app.models.entity.DisenioCalidad;

public interface IComercialTotalRazonSocialService {
	
	List<ComercialTotalRazonSocial> findAll(Long id);
	
	List<Object []> consultaX(Long id);

	void save(ComercialTotalRazonSocial total);

	void delete(Long id);
	
	
	ComercialTotalRazonSocial findOne( Long pedido , Long idFactura  );

	
	List<Object []> totalRazon(Long id);
	
	ComercialTotalRazonSocial findOne( Long id);
}



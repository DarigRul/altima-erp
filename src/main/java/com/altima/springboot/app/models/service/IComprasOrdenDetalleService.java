package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.OrdenCompraDetalleDto;
import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;

public interface IComprasOrdenDetalleService {

    void save (ComprasOrdenDetalle comprasOrdenDetalle);
	
	List<ComprasOrdenDetalle> findAll();
	
	ComprasOrdenDetalle findOne (Long id);
	
	void delete (Long id);

	List<OrdenCompraDetalleDto> findByIdOrdenCompras(Long id);
}

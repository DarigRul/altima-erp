package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.dto.OrdenComprasListDto;
import com.altima.springboot.app.models.entity.ComprasOrden;

public interface IComprasOrdenService {

    void save (ComprasOrden comprasOrden);
	
	List<ComprasOrden> findAll();
	
	ComprasOrden findOne (Long id);
	
	void delete (Long id);

	List<OrdenComprasListDto> findAllList();
}

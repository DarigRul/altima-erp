package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpAlmacen;

public interface IAmpAlmacenService {
	List<AmpAlmacen> findAll();

	void save(AmpAlmacen almacen);

	void delete(Long id);

	AmpAlmacen findOne(Long id);
	
	boolean findDuplicate(String nombre,String encargado);

}

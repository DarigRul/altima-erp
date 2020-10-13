package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.AmpAlmacenUbicacion;

public interface IAmpAlmacenUbicacion {
	
	List<AmpAlmacenUbicacion> findAll(Long id);

	void save(AmpAlmacenUbicacion ubicacion);

	void delete(Long id);

	AmpAlmacenUbicacion findOne(Long id, String nombre);
	
	AmpAlmacenUbicacion findOne(Long id);
	
	boolean findDuplicate(String nombre,String encargado);

}

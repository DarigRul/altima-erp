package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.UbicacionListDTO;
import com.altima.springboot.app.models.entity.AmpAlmacenUbicacion;

public interface IAmpAlmacenUbicacion {
	
	List<AmpAlmacenUbicacion> findAll(Long id,boolean estatus);

	void save(AmpAlmacenUbicacion ubicacion);

	void delete(Long id);

	AmpAlmacenUbicacion findOne(Long id, String nombre);
	
	AmpAlmacenUbicacion findOne(Long id);
	
	boolean findDuplicate(String nombre,String encargado);

	List<UbicacionListDTO> findAllByRollo(Long idRollo);

}

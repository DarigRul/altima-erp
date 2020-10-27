package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpAlmacenUbicacionArticulo;

public interface IAmpAlmacenUbicacionArticuloService {
    
    List<AmpAlmacenUbicacionArticulo> findAll();

	void save(AmpAlmacenUbicacionArticulo ubicacionArticulo);

	void delete(Long id);

	AmpAlmacenUbicacionArticulo findOne(Long id);
}

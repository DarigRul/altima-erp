package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPrecioComposicion;

public interface IDisenioPrecioComposicionService {

	void save(DisenioPrecioComposicion disenioComposicion);
	
	List<Object[]> findAll();
	
	DisenioPrecioComposicion findOne(Long id);
	
	void deleteOne(DisenioPrecioComposicion disenioComposicion);
}

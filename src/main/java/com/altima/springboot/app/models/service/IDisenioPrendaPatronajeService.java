package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPrendaPatronaje;

public interface IDisenioPrendaPatronajeService 
{

	List<DisenioPrendaPatronaje> findAll();

	void save(DisenioPrendaPatronaje disenioprendapatronaje);

	void delete(Long[] idPatronajes, Long id);
	
	void deleteAllPatronajeFromPrenda(Long id);
}

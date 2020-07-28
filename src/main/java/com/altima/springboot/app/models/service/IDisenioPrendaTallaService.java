package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPrendaTalla;

public interface IDisenioPrendaTallaService {

	List<DisenioPrendaTalla> findAll();

	void save(DisenioPrendaTalla disenioprendatalla);

	void delete(Long id);

	DisenioPrendaTalla findOne(Long id);

}

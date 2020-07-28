package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPrendaMarcador;

public interface IDisenioPrendaMarcadorService {
    List<DisenioPrendaMarcador> findAll();

	void save(DisenioPrendaMarcador disenioprenda);

	void delete(Long[] idMarcadores, Long id);

	void deleteByIdPrenda(Long id);

	DisenioPrendaMarcador findOne(Long id);

	List<DisenioPrendaMarcador> findByIdPrenda(Long id);

}
package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioRuta;

public interface IDisenioRutaService {

	List<DisenioRuta> findAll();

	void save(DisenioRuta disenioruta);

	void delete(Long id);

	DisenioRuta findOne(Long id);

}

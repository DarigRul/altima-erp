package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioProcesoRuta;

public interface IDisenioProcesoRutaService {

	List<DisenioProcesoRuta> findAll();

	void save(DisenioProcesoRuta disenioprocesoruta);

	void delete(Long id);

	DisenioProcesoRuta findOne(Long id);

}

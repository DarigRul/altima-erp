package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioListaPrecioPrenda;

public interface IDisenioListaPrecioPrendaService {
	
	List<DisenioListaPrecioPrenda> findAll();

	void save(DisenioListaPrecioPrenda disenioprenda);

	void delete(Long id);

	DisenioListaPrecioPrenda findOne(Long id);
	
	Object BuscarPrecioPrendaById(Long id);

	DisenioListaPrecioPrenda findByidPrenda(Long id);

	List<Object> listaPrecioPrenda(Long id);

	List<Object> listaFamPrendaByidPrenda(Long id);
}

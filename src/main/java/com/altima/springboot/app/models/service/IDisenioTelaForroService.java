package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioTelaForro;

public interface IDisenioTelaForroService {

	List<DisenioTelaForro> findAll();

	void save(DisenioTelaForro diseniotelaforro);

	void delete(Long id);
	
	void deleteEliminados(String[] idForros, Long idTela);

	DisenioTelaForro findOne(Long id);

}

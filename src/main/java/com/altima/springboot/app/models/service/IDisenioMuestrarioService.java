package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioMuestrario;

public interface IDisenioMuestrarioService {

	List<DisenioMuestrario> findAll();

	void save(DisenioMuestrario diseniomuestrario);

	void delete(Long id);

	DisenioMuestrario findOne(Long id);

}

package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioTalla;

public interface IDisenioTallaService {

	List<DisenioTalla> findAll();

	void save(DisenioTalla diseniotalla);

	void delete(Long id);

	DisenioTalla findOne(Long id);

}

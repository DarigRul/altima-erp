package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrDireccion;

public interface IHrDireccionService {

	List<HrDireccion> findAll();

	void save(HrDireccion hrdireccion);

	void delete(Long id);

	HrDireccion findOne(Long id);

}

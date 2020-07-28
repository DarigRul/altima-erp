package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrPersona;

public interface IHrPersonaService {

	List<HrPersona> findAll();

	void save(HrPersona hrpersona);

	void delete(Long id);

	HrPersona findOne(Long id);

}

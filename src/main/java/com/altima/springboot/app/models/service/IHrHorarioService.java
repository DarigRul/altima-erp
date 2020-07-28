package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrHorario;


public interface IHrHorarioService {
	
	List<HrHorario> findAll();

	void save(HrHorario hrhorario);

	void delete(Long id);

	HrHorario findOne(Long id);
	
	List<HrHorario> findAllHorarios();

	List<HrHorario> obtenerHorario(Long id);
}

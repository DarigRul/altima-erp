package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrCalendario;
import com.altima.springboot.app.models.entity.HrHorario;

public interface IHrCalendariosService {
	
	List<HrCalendario> findAll();

	void save(HrCalendario hrcalendarios);

	void delete(Long id);

	HrCalendario findOne(Long id);
	
	List<HrCalendario> findAllCalendarios();
}

package com.altima.springboot.app.models.service;

import java.util.List;
import java.util.Optional;

import com.altima.springboot.app.models.entity.AmpAlmacenFisico;
import com.altima.springboot.app.models.entity.HrEmpleado;

public interface IAmpAlmacenFisicoService {

	void save(AmpAlmacenFisico entity);

	AmpAlmacenFisico findOne(Long id);

	List<AmpAlmacenFisico> findAll();

	void deleteById(Long id);

	List<HrEmpleado> findEmployeeAMP();

	List<Object[]> findAllAMPFisico();

	List<AmpAlmacenFisico> findAMPFisicoDuplicate(String Nombre, Long Encargado);

}

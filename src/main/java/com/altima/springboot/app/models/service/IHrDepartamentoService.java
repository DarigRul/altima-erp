package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrLookup;

public interface IHrDepartamentoService {

	List<HrDepartamento> findAll();

	void save(HrDepartamento hrdepartamento);

	void delete(Long id);

	HrDepartamento findOne(Long id);

	public List<Object[]> listarDepartamentos();

	List<HrLookup> findAllAreas();

	List<HrLookup> findAllEmpresas(String tipo);

	Object obtenerDepartamento(Long id);

}

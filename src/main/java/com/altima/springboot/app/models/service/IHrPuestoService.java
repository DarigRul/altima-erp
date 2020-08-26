package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrPuesto;

public interface IHrPuestoService {

	List<HrPuesto> findAll();

	void save(HrPuesto hrpuesto);

	void delete(Long id);

	HrPuesto findOne(Long id);

	List<HrPuesto> findAllByTipoPuesto(String tipo);

	List<HrDepartamento> findAllDepartamentos();

	public List<Object[]> listarPuestos();

	public Object obtenerPuesto(Long id);

	public boolean duplicatePuesto(String nombrePuesto, String nomPlazas, String sueldos, String perfiles,
			String departamento, Boolean checkbox, String idPuesto);

}

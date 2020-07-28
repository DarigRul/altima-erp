package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import com.altima.springboot.app.models.entity.Rol;


public interface IRolService {
	public List<Rol> findAll();
	
	public Rol findOne(Long id_rol);

	public void save(Rol rol);

	public void delete(Long id_rol);

	@SuppressWarnings("rawtypes")
	public ArrayList findBySeccionRol(String seccion);

	List<Rol> FindByDepartamento();

	List<Rol> FindBySeccion();

	List<Rol> FindByPermiso(String Departamento, String seccion);

	List<Object[]> FindOneByDates(Long id);

	List<Object[]> FindRolesGenerales();

}

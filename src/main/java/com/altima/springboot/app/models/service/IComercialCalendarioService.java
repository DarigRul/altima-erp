package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ComercialCalendario;

public interface IComercialCalendarioService {
	
	List<ComercialCalendario> findAll();
	
	List<ComercialCalendario> findAllUser(Long user);
	void save (ComercialCalendario comercialCalendario);
	void delete (Long id);
	ComercialCalendario findOne(Long id);
	List<ComercialCalendario> findByClient(Long id);
	
	

}

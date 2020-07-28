package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.entity.ServicioClienteLookup;

public interface IServicioClienteLookupService {

	List<ServicioClienteLookup> findAllByType(String Tipo);

	void save(ServicioClienteLookup servicioclientelookup);

	void delete(Long id);

	ServicioClienteLookup findOne(Long id);

}

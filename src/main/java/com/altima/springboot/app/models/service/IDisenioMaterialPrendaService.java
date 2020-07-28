package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioMaterialPrenda;

public interface IDisenioMaterialPrendaService {

	List<DisenioMaterialPrenda> findAll();

	void save(DisenioMaterialPrenda diseniomaterialprenda);

	void delete(Long[] idMateriales, Long id);

	DisenioMaterialPrenda findOne(Long id);

	void deleteAllMaterialFromPrenda(Long id);
}

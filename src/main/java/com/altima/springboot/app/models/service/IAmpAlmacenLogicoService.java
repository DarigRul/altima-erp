package com.altima.springboot.app.models.service;

import java.util.List;
import java.util.Optional;

import com.altima.springboot.app.models.entity.AmpAlmacenLogico;

public interface IAmpAlmacenLogicoService {

	void save(AmpAlmacenLogico entity);

	AmpAlmacenLogico findOne(Long id);

	List<AmpAlmacenLogico> findAll();

	void deleteById(Long id);

	List<Object[]> findAllAMPLogico();

	List<AmpAlmacenLogico> findAMPLogicoDuplicate(Long AlmacenFisico, String Nombre, Long Entrada, Long Salida);

	boolean existHabilitacion();
	boolean existMaterial();
}

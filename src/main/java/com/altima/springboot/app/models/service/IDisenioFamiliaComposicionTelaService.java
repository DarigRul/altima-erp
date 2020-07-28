package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioFamiliaComposicionTela;

public interface IDisenioFamiliaComposicionTelaService {
	
	List<DisenioFamiliaComposicionTela> findAll();

	void save(DisenioFamiliaComposicionTela DisenioFamiliaComposicionTela);

	void delete(Long id);
	
	void deleteBorrados(String[] idComposicion, Long idTela);

	DisenioFamiliaComposicionTela findOne(Long id);

}
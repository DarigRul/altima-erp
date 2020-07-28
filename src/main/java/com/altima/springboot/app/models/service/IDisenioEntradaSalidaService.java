package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioEntradaSalida;

public interface IDisenioEntradaSalidaService {

	List<DisenioEntradaSalida> findAll();

	void save(DisenioEntradaSalida disenioentradasalida);

	void delete(Long id);

	DisenioEntradaSalida findOne(Long id);

}

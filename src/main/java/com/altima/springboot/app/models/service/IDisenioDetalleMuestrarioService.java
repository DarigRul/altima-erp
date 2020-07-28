package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioDetalleMuestrario;

public interface IDisenioDetalleMuestrarioService {

	List<DisenioDetalleMuestrario> findAll();

	void save(DisenioDetalleMuestrario diseniodetallemuestrario);

	void delete(Long id);

	DisenioDetalleMuestrario findOne(Long id);

}

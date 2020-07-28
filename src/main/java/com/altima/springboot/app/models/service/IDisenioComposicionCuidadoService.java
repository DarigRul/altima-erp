package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioComposicionIcuidado;

public interface IDisenioComposicionCuidadoService {

	void save(DisenioComposicionIcuidado diseniocomposicioncuidado);

	void delete(Long id);

	List<DisenioComposicionIcuidado> findAll();

	DisenioComposicionIcuidado findOne(Long id);

	List<Object> composicioncuidado(Long id_composicion);

}

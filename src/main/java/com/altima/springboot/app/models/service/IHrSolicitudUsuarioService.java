package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrSolicitudUsuario;

public interface IHrSolicitudUsuarioService {

	List<HrSolicitudUsuario> findAll();

	void save(HrSolicitudUsuario hrsolicitudusuario);

	void delete(Long id);

	HrSolicitudUsuario findOne(Long id);

}

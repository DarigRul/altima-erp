package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrSolicitud;

public interface IHrSolicitudService {

	List<HrSolicitud> findAll();

	void save(HrSolicitud hrsolicitud);

	void delete(Long id);

	HrSolicitud findOne(Long id);

}

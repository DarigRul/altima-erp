package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrSucursal;

public interface IHrSucursalService {

	List<HrSucursal> findAll();

	void save(HrSucursal hrsucursal);

	void delete(Long id);

	HrSucursal findOne(Long id);

}

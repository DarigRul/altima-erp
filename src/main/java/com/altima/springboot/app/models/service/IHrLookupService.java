package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrLookup;

public interface IHrLookupService {

	List<HrLookup> findAll();

	void save(HrLookup hrLookup) throws Exception;

	void delete(Long id);

	HrLookup findOne(Long id);

	List<HrLookup> findAllByTipoLookup(String tipo);

	public boolean findDuplicate(String LookupEmpresa);

	public boolean findDuplicateArea(String LookupArea);

}

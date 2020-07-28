package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrDocumento;

public interface IHrDocumentoService {

	List<HrDocumento> findAll();

	void save(HrDocumento hrdocumento);

	void delete(Long id);

	HrDocumento findOne(Long id);

}

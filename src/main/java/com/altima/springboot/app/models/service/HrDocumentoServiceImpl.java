package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrDocumento;
import com.altima.springboot.app.repository.HrDocumentoRepository;


@Service
public class HrDocumentoServiceImpl implements IHrDocumentoService {
	@Autowired
	private HrDocumentoRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<HrDocumento> findAll() {
		// TODO Auto-generated method stub
		return (List<HrDocumento>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrDocumento hrdocumento) {
		// TODO Auto-generated method stub
		repository.save(hrdocumento);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrDocumento findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

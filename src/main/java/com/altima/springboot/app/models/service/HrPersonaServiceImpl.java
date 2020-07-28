package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrPersona;
import com.altima.springboot.app.repository.HrPersonaRepository;


@Service
public class HrPersonaServiceImpl implements IHrPersonaService {
	@Autowired
	private HrPersonaRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<HrPersona> findAll() {
		// TODO Auto-generated method stub
		return (List<HrPersona>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrPersona hrpersona) {
		// TODO Auto-generated method stub
		repository.save(hrpersona);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrPersona findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

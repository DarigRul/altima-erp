package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioTalla;
import com.altima.springboot.app.repository.DisenioTallaRepository;


@Service
public class DisenioTallaServiceImpl implements IDisenioTallaService {
	@Autowired
	private DisenioTallaRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<DisenioTalla> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioTalla>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioTalla diseniotalla) {
		// TODO Auto-generated method stub
		repository.save(diseniotalla);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioTalla findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

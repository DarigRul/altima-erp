package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.repository.HrDireccionRepository;


@Service
public class HrDireccionServiceImpl implements IHrDireccionService {
	@Autowired
	private HrDireccionRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<HrDireccion> findAll() {
		// TODO Auto-generated method stub
		return (List<HrDireccion>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrDireccion hrdireccion) {
		// TODO Auto-generated method stub
		repository.save(hrdireccion);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrDireccion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

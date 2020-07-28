package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.repository.HrLookupRepository;
@Service
public class HrLookupServiceImpl implements IHrLookupService {
	@Autowired
	EntityManager em;
	@Autowired
	HrLookupRepository repository;
	@Override
	@Transactional
	public List<HrLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<HrLookup>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrLookup hrLookup) throws Exception{
		repository.save(hrLookup);
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public HrLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrLookup> findAllByTipoLookup(String tipo) {
		// TODO Auto-generated method stub
		
		return repository.findBytipoLookup(tipo);
	}

}

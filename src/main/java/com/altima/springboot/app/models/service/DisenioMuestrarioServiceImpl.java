package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioMuestrario;
import com.altima.springboot.app.repository.DisenioMuestrarioRepository;

@Service
public class DisenioMuestrarioServiceImpl implements IDisenioMuestrarioService {
	@Autowired
	private DisenioMuestrarioRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioMuestrario> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioMuestrario>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioMuestrario diseniomuestrario) {
		// TODO Auto-generated method stub
		repository.save(diseniomuestrario);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioMuestrario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
}

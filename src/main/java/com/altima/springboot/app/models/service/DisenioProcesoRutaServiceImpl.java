package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioProcesoRuta;
import com.altima.springboot.app.repository.DisenioProcesoRutaRepository;

@Service
public class DisenioProcesoRutaServiceImpl implements IDisenioProcesoRutaService {
	@Autowired
	private DisenioProcesoRutaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioProcesoRuta> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioProcesoRuta>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioProcesoRuta disenioprocesoruta) {
		// TODO Auto-generated method stub
		repository.save(disenioprocesoruta);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioProcesoRuta findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
}

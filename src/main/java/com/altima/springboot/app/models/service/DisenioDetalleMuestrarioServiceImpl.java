package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioDetalleMuestrario;
import com.altima.springboot.app.repository.DisenioDetalleMuestrarioRepository;

@Service
public class DisenioDetalleMuestrarioServiceImpl implements IDisenioDetalleMuestrarioService {
	@Autowired
	private DisenioDetalleMuestrarioRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioDetalleMuestrario> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioDetalleMuestrario>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioDetalleMuestrario diseniodetallemuestrario) {
		// TODO Auto-generated method stub
		repository.save(diseniodetallemuestrario);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioDetalleMuestrario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
}

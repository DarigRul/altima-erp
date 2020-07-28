package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioEntradaSalida;
import com.altima.springboot.app.repository.DisenioEntradaSalidaRepository;

@Service
public class DisenioEntradaSalidaServiceImpl implements IDisenioEntradaSalidaService {
	@Autowired
	private DisenioEntradaSalidaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioEntradaSalida> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioEntradaSalida>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioEntradaSalida disenioentradasalida) {
		// TODO Auto-generated method stub
		repository.save(disenioentradasalida);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioEntradaSalida findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
}

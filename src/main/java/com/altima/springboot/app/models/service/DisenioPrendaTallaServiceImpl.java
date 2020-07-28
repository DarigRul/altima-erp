package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioPrendaTalla;
import com.altima.springboot.app.repository.DisenioPrendaTallaRepository;

@Service
public class DisenioPrendaTallaServiceImpl implements IDisenioPrendaTallaService {
	@Autowired
	private DisenioPrendaTallaRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioPrendaTalla> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioPrendaTalla>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioPrendaTalla disenioprendatalla) {
		// TODO Auto-generated method stub
		repository.save(disenioprendatalla);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioPrendaTalla findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
}

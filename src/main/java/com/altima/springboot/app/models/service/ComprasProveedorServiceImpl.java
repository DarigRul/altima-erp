package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.repository.ComprasProveedorRepository;

@SuppressWarnings("unchecked")
@Service
public class ComprasProveedorServiceImpl implements IComprasProveedorService {
	
	@Autowired
	private ComprasProveedorRepository repository;
	
	

	@Override
	@Transactional
	public ComprasProveedores findOne(Long Id) {

		return repository.findById(Id).orElse(null);
	}

	
	@Override
	@Transactional
	public List<ComprasProveedores> findAll() {

		return (List<ComprasProveedores>) repository.findAll();
	}
	@Override
	@Transactional
	public void save(ComprasProveedores comprasProveedores) {
		
		repository.save(comprasProveedores);
	}

}

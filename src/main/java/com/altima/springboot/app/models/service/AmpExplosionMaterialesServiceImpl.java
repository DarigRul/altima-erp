package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.AmpAlmacen;
import com.altima.springboot.app.models.entity.AmpExplosionMateriales;
import com.altima.springboot.app.repository.AmpAlmacenRepository;
import com.altima.springboot.app.repository.AmpExplosionMaterialesRepository;

@Service
public class AmpExplosionMaterialesServiceImpl implements IAmpExplosionMaterialesService{

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AmpExplosionMaterialesRepository repository;

	@Override
	public List<AmpExplosionMateriales> findAll() {
		return (List<AmpExplosionMateriales>) repository.findAll();
	}

	@Override
	public void save(AmpExplosionMateriales explosionmateriales) {
		repository.save(explosionmateriales);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public AmpExplosionMateriales findOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	
}

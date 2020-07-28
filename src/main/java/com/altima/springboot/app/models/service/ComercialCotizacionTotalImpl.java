package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCotizacionTotal;
import com.altima.springboot.app.repository.ComercialCotizacionTotalRepository;

@Service
public class ComercialCotizacionTotalImpl implements IComercialCotizacionTotalService {

	@Autowired
	ComercialCotizacionTotalRepository repository;
	@Autowired
	EntityManager em;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<ComercialCotizacionTotal> findAll(Long id) {
		return null;
	}

	@Override
	public void save(ComercialCotizacionTotal comercialCotizacionTotal) {
		repository.save(comercialCotizacionTotal);
	}

	@Override
	public ComercialCotizacionTotal findOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public ComercialCotizacionTotal findByCotizacion(Long id) {
		return (ComercialCotizacionTotal) em.createQuery("FROM ComercialCotizacionTotal WHERE idCotizacion="+id).getSingleResult();
	}
	
}

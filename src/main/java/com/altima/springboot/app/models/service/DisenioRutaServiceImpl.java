package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioRuta;
import com.altima.springboot.app.repository.DisenioRutaRepository;


@Service
public class DisenioRutaServiceImpl implements IDisenioRutaService {
	@Autowired
	private DisenioRutaRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public List<DisenioRuta> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioRuta>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioRuta disenioruta) {
		// TODO Auto-generated method stub
		if(disenioruta.getIdRuta()!=null &&  disenioruta.getIdRuta()>0) {
			em.merge(disenioruta);
		}
		else {
			em.persist(disenioruta);
		}

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioRuta findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

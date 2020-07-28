package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.CatalogoRepository;


@Service
public class CatalogoServiceImpl implements ICatalogoService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CatalogoRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<DisenioLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioLookup>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioLookup diseniolookup) {
		// TODO Auto-generated method stub
		repository.save(diseniolookup);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<DisenioLookup> findAllLookup(String Tipo) {
		return em.createQuery("from DisenioLookup where tipo_lookup='"+Tipo+"'").getResultList();
	}
	
	@Override
	@Transactional
	public DisenioLookup findLastLookupByType(String Tipo){
		return (DisenioLookup) em.createQuery("from DisenioLookup where tipo_lookup='"+Tipo+"' ORDER BY idLookup DESC").setMaxResults(1).getSingleResult();
	}
	 
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<DisenioLookup> result = em.createQuery("from DisenioLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"'").getResultList();
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo,String atributo){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<DisenioLookup> result = em.createQuery("from DisenioLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"' and atributo1='"+atributo+"'").getResultList();
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}

	

}

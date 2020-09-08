package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OrderBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;

@Service
public class DisenioLookupServiceImpl implements IDisenioLookupService {
	
	@Autowired
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<DisenioLookup> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from alt_disenio_lookup").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<DisenioLookup> findByTipoLookup(String tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipoLookup='" + tipo + "' and Estatus=1").getResultList();
	}
	
	@Override
	@Transactional
	public void save(DisenioLookup lookup) {
		// TODO Auto-generated method stub
		if (lookup.getIdLookup() != null) {
			em.merge(lookup);
		} else {
			em.persist(lookup);
		}
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}
	
	@Override
	public DisenioLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(DisenioLookup.class, id);
	}
	
}

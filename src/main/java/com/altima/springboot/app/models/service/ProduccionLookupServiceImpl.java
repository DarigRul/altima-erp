package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.repository.ProduccionLookupRepository;
@Service
public class ProduccionLookupServiceImpl implements IProduccionLookupService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ProduccionLookupRepository repository;

	@Override
	@Transactional(readOnly=true)
	public List<ProduccionLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionLookup>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(ProduccionLookup ProduccionLookup) {
		// TODO Auto-generated method stub
		repository.save(ProduccionLookup);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public ProduccionLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<ProduccionLookup> findAllLookup(String Tipo) {
		return em.createQuery("from ProduccionLookup where tipo_lookup='"+Tipo+"'").getResultList();
	}
	
	@Override
	@Transactional
	public ProduccionLookup findLastLookupByType(String Tipo){
		return (ProduccionLookup) em.createQuery("from ProduccionLookup where tipo_lookup='"+Tipo+"' ORDER BY idLookup DESC").setMaxResults(1).getSingleResult();
	}
	 
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<ProduccionLookup> result = em.createQuery("from ProduccionLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"'").getResultList();
		
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
	public boolean findDuplicate(String Lookup,String Tipo,String descripcion){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<ProduccionLookup> result = em.createQuery("from ProduccionLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"' and descripcion_lookup= '"+descripcion +"'").getResultList();
		
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
	public boolean findDuplicate(String Lookup,String Tipo,String atributo1, String atributo2, String descripcion){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		
		List<ProduccionLookup> result = em.createQuery("from ProduccionLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"' and atributo1='"+atributo1+"' and atributo2='"+atributo2+"' and descripcion_lookup='"+descripcion+"'" ).getResultList();
		
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
	@SuppressWarnings("unchecked")
	public List<ProduccionLookup> findAllByType(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ProduccionLookup where Estatus=1 and tipoLookup='"+Tipo+"'").getResultList();
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProduccionLookup> findAllByType(String Posicion,String Genero ,String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ProduccionLookup where Estatus=1 and tipoLookup='Talla' and descripcionLookup='"+Posicion+"' and atributo1='"+Genero+"' ").getResultList();
	}


}

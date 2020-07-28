package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialCalendario;
import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.entity.ServicioClienteLookup;
import com.altima.springboot.app.repository.ComercialCalendarioRepository;
import com.altima.springboot.app.repository.ServicioClienteLookupRepository;

@Service
public class ServicioClienteLookupServiceImpl implements IServicioClienteLookupService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired 
	private ServicioClienteLookupRepository repository;
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ServicioClienteLookup> findAllByType(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ServicioClienteLookup where estatus=1 and tipoLookup='"+Tipo+"'").getResultList();
	}

	@Override
	public void save(ServicioClienteLookup servicioclientelookup) {
		repository.save(servicioclientelookup);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public ServicioClienteLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	

}

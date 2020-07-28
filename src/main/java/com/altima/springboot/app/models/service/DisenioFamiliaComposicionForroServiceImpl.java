package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioFamiliaComposicionForro;
import com.altima.springboot.app.repository.DisenioFamiliaComposicionForroRepository;

@Service
public class DisenioFamiliaComposicionForroServiceImpl implements IDisenioFamiliaComposicionForroService {
	
	@Autowired
	private DisenioFamiliaComposicionForroRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<DisenioFamiliaComposicionForro> findAll() {
		return (List<DisenioFamiliaComposicionForro>) repository.findAll();
		
	}
	
	@Override
	public void save(DisenioFamiliaComposicionForro DisenioFamiliaComposicionForro) {
		repository.save(DisenioFamiliaComposicionForro);
		
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
		
	}
	
	@Override
	public DisenioFamiliaComposicionForro findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void deleteLista(Long id) {
		
		Query query = em.createNativeQuery("delete from alt_disenio_familia_composicion_forro \r\n"
				+ "WHERE alt_disenio_familia_composicion_forro.id_forro=" + id);
		
		query.executeUpdate();
		
	}
}

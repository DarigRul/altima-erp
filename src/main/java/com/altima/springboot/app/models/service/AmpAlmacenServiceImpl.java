package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altima.springboot.app.models.entity.AmpAlmacen;
import com.altima.springboot.app.repository.AmpAlmacenRepository;

@Service
public class AmpAlmacenServiceImpl implements IAmpAlmacenService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AmpAlmacenRepository repository;

	@Override
	public List<AmpAlmacen> findAll() {
		return (List<AmpAlmacen>) repository.findAll();
	}

	@Override
	public void save(AmpAlmacen almacen) {
		repository.save(almacen);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public AmpAlmacen findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public boolean findDuplicate(String nombre, String encargado) {
		boolean duplicate;
		@SuppressWarnings("unchecked")
		
		List<AmpAlmacen> result = em.createQuery("from AmpAlmacen where nombre_almacen='"+nombre+"' and encargado='"+encargado+"'").getResultList();
		if(result.isEmpty()) {
			duplicate=false;
			
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}

}

package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.DisenioRutaProceso;
import com.altima.springboot.app.repository.DisenioRutaProcesoRepository;

@Service
public class DisenioRutaProcesoServiceImpl implements IDisenioRutaProcesoService {

	@Autowired
	private EntityManager em;
	
	@Autowired
	private DisenioRutaProcesoRepository repository;
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<DisenioRutaProceso> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from alt_disenio_ruta_proceso").getResultList();
	}

	@Transactional
	@Override
	public DisenioRutaProceso findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(DisenioRutaProceso.class, id);
	}

	@Transactional
	@Override
	public List<DisenioRutaProceso> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void save(DisenioRutaProceso rutaProceso) {
		// TODO Auto-generated method stub
		repository.save(rutaProceso);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findByRuta(Long id) {
		// TODO Auto-generated method stub
		
		return em.createNativeQuery("select p.id_ruta_proceso, p.id_proceso, nom.nombre_lookup from \r\n" + 
				"(select * from alt_disenio_ruta_proceso where id_ruta = "+id+") as p \r\n" + 
				"inner join alt_disenio_lookup nom on p.id_proceso = nom.id_lookup").getResultList();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByRutaEntity(Long id) {
		// TODO Auto-generated method stub
		
		return em.createNativeQuery("select p.id_ruta_proceso, p.id_proceso, nom.nombre_lookup from \r\n" + 
				"(select * from alt_disenio_ruta_proceso where id_ruta = "+id+") as p \r\n" + 
				"inner join alt_disenio_lookup nom on p.id_proceso = nom.id_lookup").getResultList();
	}

}

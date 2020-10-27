package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AmpAlmacenUbicacion;
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.repository.AmpAlmacenUbicacionRepository;

@Service
public class AmpAlmacenUbicacionService implements IAmpAlmacenUbicacion {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AmpAlmacenUbicacionRepository repository;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<AmpAlmacenUbicacion> findAll(Long id,boolean estatus) {
		if (estatus) {
		return em.createQuery("from AmpAlmacenUbicacion where estatus=1 and id_almacen_fisico="+id).getResultList();
		} else {
		return em.createQuery("from AmpAlmacenUbicacion where id_almacen_fisico="+id).getResultList();
		}
	}

	@Override
	public void save(AmpAlmacenUbicacion ubicacion) {
		repository.save(ubicacion);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public AmpAlmacenUbicacion findOne(Long id, String nombre) {
		try {
			return (AmpAlmacenUbicacion) em.createQuery("from AmpAlmacenUbicacion where id_almacen_fisico="+id + "and nombre='"+nombre+"'").getSingleResult();
			}
			catch(Exception e) {
				return null;
			}
		
	}
	
	@Override
	public AmpAlmacenUbicacion findOne(Long id) {
		return repository.findById(id).orElse(null);
		
	}

	@Override
	public boolean findDuplicate(String nombre, String encargado) {
		// TODO Auto-generated method stub
		return false;
	}

}

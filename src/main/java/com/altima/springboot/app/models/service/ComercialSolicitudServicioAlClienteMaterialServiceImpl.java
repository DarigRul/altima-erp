package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteMaterial;
import com.altima.springboot.app.repository.ComercialSolicitudServicioAlClienteMaterialRepository;

@Service
public class ComercialSolicitudServicioAlClienteMaterialServiceImpl implements IComercialSolicitudServicioAlClienteMaterialService{
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialSolicitudServicioAlClienteMaterialRepository repository;
	
	@Override
	public void save(ComercialSolicitudServicioAlClienteMaterial ComercialSolicitudServicioAlClienteMaterial) {
		// TODO Auto-generated method stub
		repository.save(ComercialSolicitudServicioAlClienteMaterial);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComercialSolicitudServicioAlClienteMaterial> findBySolicitud(Long idSolicitud) {
		// TODO Auto-generated method stub
		return (List<ComercialSolicitudServicioAlClienteMaterial>) em.createQuery("FROM ComercialSolicitudServicioAlClienteMaterial WHERE idSolicitudServicioAlCliente = " + idSolicitud).getResultList();
	}

}

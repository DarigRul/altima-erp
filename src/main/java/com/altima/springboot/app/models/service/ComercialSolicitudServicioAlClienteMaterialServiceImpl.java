package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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


	@Transactional(readOnly = true)
	@Override
	public Integer buscarMaterial(Long id , String material) {
		String re = em.createNativeQuery("SELECT COUNT(estatus)"+
		" from alt_comercial_solicitud_servicio_al_cliente_material where material= '"+material+"' and id_solicitud_servicio_al_cliente = "+id).getSingleResult().toString();
	
		return Integer.parseInt(re);

	}

}

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

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBySolicitudId(Long idSolicitud) {
	
		List<Object[]> response = em.createNativeQuery("SELECT "+
			"solicitud.id_solicitud_servicio_al_cliente_material, "+
			"material2.nombre_lookup, "+
			"solicitud.cantidad "+
		"FROM "+
			"alt_servicio_cliente_lookup AS material2 "+
			"INNER JOIN alt_comercial_solicitud_servicio_al_cliente_material AS solicitud ON material2.id_lookup = solicitud.id_lookup "+
		"WHERE "+
			"material2.tipo_lookup = 'Material' "+
			"AND solicitud.id_solicitud_servicio_al_cliente = "+idSolicitud).getResultList();
		return response;
	}

	@Transactional(readOnly = true)
	@Override
	public String nombreMaterial( Long material) {
		String re = em.createNativeQuery("SELECT look.nombre_lookup  FROM alt_servicio_cliente_lookup AS look WHERE look.id_lookup = "+material).getSingleResult().toString();
	
		return re;

	}

}

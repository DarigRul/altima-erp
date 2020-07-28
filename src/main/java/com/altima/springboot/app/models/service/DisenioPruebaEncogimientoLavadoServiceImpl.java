package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;
import com.altima.springboot.app.repository.DisenioPruebaEncogimientoLavadoRepository;

@Service
public class DisenioPruebaEncogimientoLavadoServiceImpl implements IDisenioPruebaEncogimientoLavadoService {

	@Autowired
	private DisenioPruebaEncogimientoLavadoRepository repository;
	
	@Autowired
	private EntityManager em;
	@Override
	public List<DisenioPruebaEncogimientoLavado> findAll(DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado) {

		repository.save(pruebaEncogimientoLavado);
	}

	@Override
	public int ifExist(Long id) {
		
		return Integer.parseInt(em.createNativeQuery("SELECT IF((SELECT COUNT(*)\r\n" + 
				" FROM alt_disenio_prueba_encogimiento_lavado \r\n" + 
				"	WHERE id_calidad = "+id+")>0, 1 , 0)").getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioPruebaEncogimientoLavado> findAllByCalidad(Long id) {
		
		return em.createQuery("FROM DisenioPruebaEncogimientoLavado \r\n" + 
				"	WHERE idCalidad ="+id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public DisenioPruebaEncogimientoLavado findByTipoPrueba(String tipo, Long id) {

		return (DisenioPruebaEncogimientoLavado) em.createQuery("from DisenioPruebaEncogimientoLavado where idCalidad="+id+" and tipoPrueba = '"+tipo+"'").getSingleResult();
	}

	@Override
	public int ifExistLavado(Long id, String tipo) {
		
		return Integer.parseInt(em.createNativeQuery("SELECT IF((SELECT COUNT(*)\r\n" + 
				" FROM alt_disenio_prueba_encogimiento_lavado \r\n" + 
				"	WHERE id_calidad = "+id+" AND tipo_prueba = '"+tipo+"')>0, 1 , 0)").getSingleResult().toString());
	}
}

package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioPruebaLavadoContaminacionCostura;
import com.altima.springboot.app.repository.IDisenioPruebaLavadoContaminacionCosturaRepository;

@Service
public class DisenioPruebaLavadoContaminacionCosturaServiceImpl implements IDisenioPruebaLavadoContaminacionCosturaService {

	@Autowired
	private IDisenioPruebaLavadoContaminacionCosturaRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional
	public void save(DisenioPruebaLavadoContaminacionCostura PruebasLCC) {
		
		repository.save(PruebasLCC);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioPruebaLavadoContaminacionCostura> findAllByCalidad(Long id) {

		return em.createQuery("FROM DisenioPruebaLavadoContaminacionCostura \r\n " + 
								"WHERE idCalidad =" +id).getResultList();
	}

	@Override
	@Transactional
	public int ifExist(Long id) {

		return Integer.parseInt(em.createNativeQuery("SELECT IF((SELECT COUNT(*) \r\n" + 
														"FROM alt_disenio_prueba_lavado_contaminacion_costura \r\n" + 
															"WHERE id_calidad = "+id+")>0, 1 , 0)").getSingleResult().toString());
	}

	@Override
	public DisenioPruebaLavadoContaminacionCostura findByTipoPrueba(String tipo, Long id) {
		
		return (DisenioPruebaLavadoContaminacionCostura) em.createQuery("from DisenioPruebaLavadoContaminacionCostura where idCalidad="+id+" and tipoPrueba = '"+tipo+"'").getSingleResult();
	}

	@Override
	@Transactional
	public int ifExistContaCostura(Long id, String tipo) {

		return Integer.parseInt(em.createNativeQuery("SELECT IF((SELECT COUNT(*) \r\n" + 
														"FROM alt_disenio_prueba_lavado_contaminacion_costura \r\n" + 
															"WHERE id_calidad = "+id+" AND tipo_prueba= '"+tipo+"')>0, 1 , 0)").getSingleResult().toString());
	}
}

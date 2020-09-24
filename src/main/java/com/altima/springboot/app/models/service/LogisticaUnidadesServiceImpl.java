package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.models.entity.LogisticaUnidad;
import com.altima.springboot.app.repository.LogisticaUnidadesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogisticaUnidadesServiceImpl implements ILogisticaUnidadesService {

	@Autowired
	private LogisticaUnidadesRepository unidades;
	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<LogisticaUnidad> findAll() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("Call alt_pr_logistica_unidades").getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<HrPuesto> findAllPosition() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("Call alt_pr_logistica_choferes").getResultList();
	}

	@Override
	@Transactional
	public void save(LogisticaUnidad unidad) {
		// TODO Auto-generated method stub
		unidades.save(unidad);
	}

	@Override
	@Transactional
	public LogisticaUnidad findOne(Long id) {
		// TODO Auto-generated method stub
		return unidades.findById(id).orElse(null);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean findOneByPlaca(String unidadDuplicado)  {
		boolean duplicate;
		List<LogisticaUnidad> result = em.createNativeQuery("SELECT * FROM alt_logistica_unidad l WHERE l.id_text = '" + unidadDuplicado +"'").getResultList();
		if (result.isEmpty()) {
			duplicate = false;
		} else {
			duplicate = true;
		}
		return duplicate;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean findOneById(Long id)  {
		boolean duplicate;
		List<LogisticaUnidad> result = em.createNativeQuery("SELECT * FROM alt_logistica_unidad l WHERE l.id_unidad = '" + id +"'").getResultList();
		if (result.isEmpty()) {
			duplicate = false;
		} else {
			duplicate = true;
		}
		return duplicate;
	}

}
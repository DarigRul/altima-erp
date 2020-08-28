package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrIncrementoPlaza;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.repository.HrIncrementoPlazaRepository;

@Service
public class HrIncrementoPlazaServiceImpl implements IHrIncrementoPlazaService {

	@Autowired
	HrIncrementoPlazaRepository repository;
	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public List<HrIncrementoPlaza> findAll() {
		// TODO Auto-generated method stub
		return (List<HrIncrementoPlaza>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrIncrementoPlaza incremento) {
		// TODO Auto-generated method stub
		repository.save(incremento);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrIncrementoPlaza findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> incrementosPlazas() {
		List<Object[]> mv;
		mv = em.createNativeQuery("{call  alt_pr_incremento_plazas}").getResultList();
		return mv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object editarPlazas(Long id) {
		List<Object[]> mv;
		mv = em.createNativeQuery("{call  alt_pr_editar_incrementosPlazas(" + id + ")}").getResultList();
		return mv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object listarDepartamentos(Long id) {
		List<Object[]> mv;
		mv = em.createNativeQuery("{call  alt_pr_departamentos(" + id + ")}").getResultList();
		return mv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object listarPuestos(Long id) {
		List<Object[]> mv;
		mv = em.createNativeQuery("{call  alt_pr_puestos(" + id + ")}").getResultList();
		return mv;
	}

	@Override
	public Object findByIdIncrementoPlaza(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("{call  alt_pr_reporteIncrementosPlazas(" + id + ")}").getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrPuesto> findAllPuestos() {
		// TODO Auto-generated method stub
		return em.createQuery("from HrPuesto").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrLookup> findAllEmpresas() {
		// TODO Auto-generated method stub
		return em.createQuery("from HrLookup where tipoLookup = 'Area'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrDepartamento> findAllDepartamentos() {
		// TODO Auto-generated method stub
		return em.createQuery("from HrDepartamento").getResultList();
	}

}

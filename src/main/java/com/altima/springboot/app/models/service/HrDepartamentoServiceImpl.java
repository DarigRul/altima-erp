package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.repository.HrDepartamentoRepository;

@Service
@SuppressWarnings("unchecked")
public class HrDepartamentoServiceImpl implements IHrDepartamentoService {
	@Autowired
	EntityManager em;
	@Autowired
	HrDepartamentoRepository repository;

	@Override
	@Transactional
	public void save(HrDepartamento hrdepartamento) {
		repository.save(hrdepartamento);
	}

	@Override
	@Transactional
	public HrDepartamento findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public List<HrDepartamento> findAll() {
		return (List<HrDepartamento>) repository.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public List<HrLookup> findAllAreas() {
		return em.createQuery("from HrLookup").getResultList();
	}
	
	@Override
	@Transactional
	public List<HrDepartamento> findAllDepartamentosByArea(Long idArea) {
		return em.createQuery("from HrDepartamento WHERE idArea = "+idArea).getResultList();
	}

	
	@Override
	@Transactional
	public List<HrLookup> findAllEmpresas(String tipo) {
		return em.createQuery("from HrLookup where tipoLookup='" + tipo + "'").getResultList();
	}

	@Override
	public List<Object[]> listarDepartamentos() {
		List<Object[]> mv;
		mv = em.createNativeQuery("{call alt_pr_listarDepartamentos}").getResultList();
		return mv;
	}

	@Override
	public Object obtenerDepartamento(Long id) {
		return em.createNativeQuery(
				"SELECT	dp.id_departamento AS idDepartamento, dp.nombre_departamento AS nombreDepa, ar.id_lookup AS idArea, ar.nombre_lookup AS nombreArea, ar.estatus AS estatus"
						+ " FROM alt_hr_departamento dp INNER JOIN alt_hr_lookup ar ON dp.id_area = ar.id_lookup"
						+ " WHERE ar.id_lookup = dp.id_area AND dp.id_departamento = " + id)
				.getSingleResult();
	}

	@Override
	public Boolean duplicateDepartamento(String nombreDepartamento, String nomArea) {
		boolean d;
		List<HrLookup> result = em.createNativeQuery("SELECT" +
		"	d.id_departamento," +
		"	d.id_area," +
		"	d.nombre_departamento," +
		"	d.id_text AS textdep," +
		"	d.actualizado_por," +
		"	d.creado_por," +
		"	d.estatus," +
		"	d.fecha_creacion," +
		"	a.nombre_lookup," +
		"	a.id_lookup," +
		"	a.id_text AS textarea " +
		"FROM" +
		"	alt_hr_departamento d" +
		"	INNER JOIN alt_hr_lookup a ON d.id_area = a.id_lookup " +
		"WHERE" +
		"	d.nombre_departamento = '"+nombreDepartamento+"' " +
		"	AND a.nombre_lookup = '"+nomArea+"'").getResultList();
		if (result.isEmpty()) {
			d = false;
		} else {
			d = true;
		}
		return d;
	}
}
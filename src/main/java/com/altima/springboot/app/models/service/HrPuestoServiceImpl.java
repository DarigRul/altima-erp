package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.repository.HrPuestoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrPuestoServiceImpl implements IHrPuestoService {
	@Autowired
	EntityManager em;
	@Autowired
	private HrPuestoRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<HrPuesto> findAll() {
		return (List<HrPuesto>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrPuesto hrpuesto) {
		repository.save(hrpuesto);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrPuesto findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrPuesto> findAllByTipoPuesto(String tipo) {
		return em.createQuery("From HrPuesto where nombrePuesto='" + tipo + "'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrDepartamento> findAllDepartamentos() {
		return em.createQuery("from HrDepartamento").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrPuesto> findAllPuestoByDepartamento(Long idDepartamento) {
		return em.createQuery("from HrPuesto WHERE idDepartamento = "+idDepartamento).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listarPuestos() {

		List<Object[]> mv;
		mv = em.createNativeQuery("{call alt_pr_listarPuestosCatalogo}").getResultList();
		return mv;
	}

	@Override
	public Object obtenerPuesto(Long id) {
		return em.createNativeQuery(
				"SELECT pt.id_puesto as idPuesto, pt.nombre_puesto as nombrePuesto, dp.id_departamento as idDepartamento, dp.nombre_departamento as nombreDepa, pt.tiempo_extra as tiempoExtra, pt.nombre_plaza as nombrePlaza, pt.sueldo as sueldo, pt.perfil as perfiles FROM alt_hr_puesto pt INNER JOIN alt_hr_departamento dp ON dp.id_departamento = pt.id_departamento WHERE pt.id_puesto = "
						+ id + " ORDER BY pt.fecha_creacion DESC;")
				.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean duplicatePuesto(String nombrePuesto, String nomPlazas, String sueldos, String perfiles,
			String departamento, Boolean checkbox, String idPuesto) {
		boolean d;
		List<HrPuesto> result = em.createNativeQuery("SELECT" +
		"	* " +
		"FROM" +
		"	alt_hr_puesto p " +
		"WHERE" +
		"	p.nombre_puesto = '"+nombrePuesto+"' " +
		"	AND p.nombre_plaza = '"+nomPlazas+"' " +
		"	AND p.sueldo = '"+sueldos+"' " +
		"	AND p.perfil = '"+perfiles+"' " +
		"	AND p.id_departamento = '"+departamento+"' " +
		"	AND p.tiempo_extra = '"+checkbox+"' " +
		"	AND p.id_puesto = '"+idPuesto+"'").getResultList();
		if (result.isEmpty()) {
			d = false;
		} else {
			d = true;
		}
		return d;
	}
}

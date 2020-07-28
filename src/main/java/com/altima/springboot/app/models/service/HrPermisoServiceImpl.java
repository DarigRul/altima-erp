
package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.HrPermiso;
import com.altima.springboot.app.repository.HrPermisoRepository;


@Service
public class HrPermisoServiceImpl implements IHrPermisoService {
	@Autowired
	private HrPermisoRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public List<HrPermiso> findAll() {
		return (List<HrPermiso>) repository.findAll();
	}

	@Override
	@Transactional
	
	public void save(HrPermiso hrpermiso) {
		repository.save(hrpermiso);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public HrPermiso findOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrPermiso> findListaPermiso() {
		
		return em.createNativeQuery(
				"SELECT\r\n" + 
				"    emp.id_text AS NumeroEmpleado,\r\n" + 
				"    emp.nombre_persona AS Nombre,\r\n" + 
				"    per.fecha_aplicacion,\r\n" + 
				"    per.hora_inicial,\r\n" + 
				"    per.hora_final,\r\n" + 
				"    per.tipo_permiso,\r\n" + 
				"    per.evento,\r\n" + 
				"    per.estatus_permiso,\r\n" + 
				"    per.utilizado,\r\n" + 
				"    per.condiciones,\r\n" + 
				"    per.observaciones,\r\n" + 
				"    per.id_permiso,\r\n" + 
				"    lk.nombre_lookup AS Area,\r\n" + 
				"    dep.nombre_departamento AS Departamento,\r\n" + 
				"    pue.nombre_puesto AS Puesto\r\n" + 
				"FROM\r\n" + 
				"    alt_hr_permiso per\r\n" + 
				"INNER JOIN alt_hr_empleado emp  ON\r\n" + 
				"	per.id_empleado = emp.id_empleado\r\n" + 
				"    AND per.estatus = 1\r\n" + 
				"INNER JOIN alt_hr_puesto pue ON \r\n" + 
				"	emp.id_puesto = pue.id_puesto\r\n" + 
				"INNER JOIN alt_hr_departamento dep ON\r\n" + 
				"	pue.id_departamento = dep.id_departamento\r\n" + 
				"INNER JOIN alt_hr_lookup lk ON\r\n" + 
				"	dep.id_area = lk.id_lookup")
				.getResultList();
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrEmpleado> findEmpleados(Long id){
		
	return em.createNativeQuery("SELECT\r\n" + 
			"	emp.id_empleado,\r\n" + 
			"    emp.nombre_persona\r\n" + 
			"    \r\n" + 
			"FROM\r\n" + 
			"    alt_hr_empleado emp\r\n" + 
			"    \r\n" + 
			"INNER JOIN alt_hr_puesto pue ON emp.id_puesto  = pue.id_puesto\r\n" + 
			"\r\n" + 
			"WHERE emp.id_puesto =" + id).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object listarDepartamentos(Long id) {
		
		List<Object[]> mv;
		mv = em.createNativeQuery("SELECT\r\n" + 
				"    dep.id_departamento,\r\n" + 
				"    dep.nombre_departamento\r\n" + 
				"    \r\n" + 
				"FROM\r\n" + 
				"    alt_hr_departamento dep\r\n" + 
				"    \r\n" + 
				"INNER JOIN alt_hr_lookup loo ON loo.id_lookup = dep.id_area\r\n" + 
				"\r\n" + 
				"WHERE loo.id_lookup =" + id).getResultList();
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object listarPuestos(Long id) {
		
		List<Object[]> mv;
		mv = em.createNativeQuery("SELECT\r\n" + 
				"	puesto.id_puesto,\r\n" + 
				"	puesto.nombre_puesto \r\n" + 
				"FROM\r\n" + 
				"	alt_hr_puesto AS puesto \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND puesto.id_departamento = "+id).getResultList();
		return mv;
	}

	@Override
	public Long area(Long id) {
		try {
			String re = em.createNativeQuery("SELECT id_area\r\n" + 
					"FROM alt_hr_departamento AS Area\r\n" + 
					"WHERE 1=1\r\n" + 
					"AND Area.id_departamento ="+id).getSingleResult().toString();
					
			return Long.parseLong(re);
			}
			catch(Exception e) {
				
				return null;
			}
	}

	@Override
	public Long departamento(Long id) {
			try {
				String re = em.createNativeQuery("SELECT id_departamento\r\n" + 
						"FROM alt_hr_puesto AS Dep\r\n" + 
						"WHERE 1=1\r\n" + 
						"AND Dep.id_puesto ="+id).getSingleResult().toString();
						
				return Long.parseLong(re);
				}
				catch(Exception e) {
					
		return null;
				}

	}

	@Override
	public Long puesto(Long id) {
		try {
			String re = em.createNativeQuery("SELECT  id_puesto \r\n" + 
					"FROM alt_hr_empleado AS Empleado\r\n" + 
					"WHERE 1=1\r\n" + 
					"AND Empleado.id_empleado ="+id).getSingleResult().toString();
					
			return Long.parseLong(re);
			}
			catch(Exception e) {
		return null;
			}
	
	}
}

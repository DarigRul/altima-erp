package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.repository.HrEmpleadoRepository;


@Service
public class HrEmpleadoServiceImpl implements IHrEmpleadoService {
	@Autowired
	private HrEmpleadoRepository repository;
	@Autowired
	private EntityManager em;
	@Override
	@Transactional(readOnly=true)
	public List<HrEmpleado> findAll() {
		// TODO Auto-generated method stub
		return (List<HrEmpleado>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrEmpleado hrempleado) {
		// TODO Auto-generated method stub
		repository.save(hrempleado);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrEmpleado findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> findAllByPuesto(String nombrePuesto) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("select id_empleado, nombre_persona, apellido_paterno, apellido_materno from alt_hr_empleado AS empleado\r\n" + 
				"		INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\r\n" + 
				"		where puesto.nombre_puesto ='"+nombrePuesto+"'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> findAllByDepartamento(String nombreDepartamento) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("select id_empleado, nombre_persona, apellido_paterno, apellido_materno from alt_hr_empleado AS empleado\r\n" + 
				"						INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\r\n" + 
				"						INNER JOIN alt_hr_departamento departamento ON puesto.id_departamento = departamento.id_departamento\r\n" + 
				"						where departamento.nombre_departamento ='"+nombreDepartamento+"'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> findEmpleadoPersona() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("Call alt_pr_empleados").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object findEmpleadoById(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("Call alt_pr_empleado("+id+")").getResultList();
	}

	@Override
	public List<HrEmpleado> findAllEmpleado() {
		// TODO Auto-generated method stub
		return repository.findAllEmpleados();
	}

}

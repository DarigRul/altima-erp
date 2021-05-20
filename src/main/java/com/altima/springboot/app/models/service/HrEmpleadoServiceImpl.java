package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.dto.AgenteVentasListDTO;
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
	public List<Object> findAllByPuestoWithoutAgenteLogued(String nombrePuesto, Long idAgente) {
		// TODO Auto-generated method stub

		return em.createNativeQuery("select id_empleado, nombre_persona, apellido_paterno, apellido_materno from alt_hr_empleado AS empleado\r\n" + 
				"		INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\r\n" + 
				"		where puesto.nombre_puesto ='"+nombrePuesto+"' "
						+ "AND empleado.id_empleado!="+idAgente).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllByPuestoDepartamentoArea(Long idPuesto, Long idDepartamento, Long idLookup) {
		// TODO Auto-generated method stub

		return em.createNativeQuery("SELECT empleado.id_empleado,\n" + 
									"		empleado.id_text,\n" + 
									"		empleado.nombre_persona, \n" + 
									"		empleado.apellido_paterno, \n" + 
									"		empleado.apellido_materno \n" + 
									"FROM alt_hr_empleado AS empleado\n" + 
									"INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\n" + 
									"INNER JOIN alt_hr_departamento depa ON puesto.id_departamento = depa.id_departamento\n" + 
									"INNER JOIN alt_hr_lookup lookup ON depa.id_area = lookup.id_lookup\n" + 
									
									"WHERE puesto.id_puesto ="+idPuesto+" \n" + 
									"AND depa.id_departamento ="+idDepartamento+" \n" + 
									"AND lookup.id_lookup = "+idLookup).getResultList();
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
	
	@Override
	@Transactional
	public Object[] findDatosPuesto(Long idEmpleado) {
		// TODO Auto-generated method stub
		return (Object[]) em.createNativeQuery("SELECT  empleado.id_empleado, \n" + 
											   "		puesto.id_puesto, \n" + 
											   "		depa.id_departamento, \n" + 
											   "		lookup.id_lookup FROM alt_hr_empleado AS empleado\n" + 
											   "INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\n" + 
											   "INNER JOIN alt_hr_departamento depa ON puesto.id_departamento = depa.id_departamento\n" + 
											   "INNER JOIN alt_hr_lookup lookup ON depa.id_area = lookup.id_lookup\n" + 
											   "\n" + 
											   "WHERE empleado.id_empleado = "+idEmpleado).getSingleResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AgenteVentasListDTO> findAllAgenteVentas() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT ahe.id_empleado as id_agente_ventas,CONCAT(ahe.nombre_persona,' ',ahe.apellido_paterno,' ',ahe.apellido_materno) as nombre_agente_ventas FROM alt_comercial_agentes_venta acav INNER JOIN alt_hr_empleado ahe ON ahe.id_empleado=acav.id_empleado WHERE ahe.estatus=1 AND acav.estatus=1",AgenteVentasListDTO.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrEmpleado> findEmpleadosSelect() {
		return em.createQuery("SELECT idEmpleado,CONCAT(nombrePersona,' ',apellidoPaterno,' ',apellidoPaterno) as nombrePersona FROM HrEmpleado WHERE estatus=1").getResultList();
	}
}

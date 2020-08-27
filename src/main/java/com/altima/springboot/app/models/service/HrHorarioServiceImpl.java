package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.HrHorario;
import com.altima.springboot.app.repository.HrHorarioRepository;

@Service
public class HrHorarioServiceImpl implements IHrHorarioService {

	@Autowired
	HrHorarioRepository repository;
	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public List<HrHorario> findAll() {
		// TODO Auto-generated method stub
		return (List<HrHorario>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrHorario hrhorario) {
		// TODO Auto-generated method stub
		repository.save(hrhorario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrHorario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrHorario> findAllHorarios() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT * FROM alt_hr_horario hr ORDER BY hr.fecha_creacion DESC").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrHorario> obtenerHorario(Long id) {
		return em.createNativeQuery("SELECT" + "	h.id_horario," + "	h.hora_inicial," + "	h.hora_final,"
				+ "	h.inicio_comida," + "	h.final_comida," + "	h.estatus " + "FROM" + "	alt_hr_horario h "
				+ "WHERE" + "	h.id_horario = " + id).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean duplicateHorario(String horaInicio, String horaSalida, String horaComida, String horaRegresoComida) {
		boolean d;
		List<HrHorario> result = em.createNativeQuery("SELECT" +
		"	* " +
		"FROM" +
		"	alt_hr_horario h " +
		"WHERE" +
		"	h.hora_inicial = '"+horaInicio+"' " +
		"	AND h.hora_final = '"+horaSalida+"' " +
		"	AND h.inicio_comida = '"+horaComida+"' " +
		"	AND h.final_comida = '"+horaRegresoComida+"'").getResultList();
		if (result.isEmpty()) {
			d = false;
		} else {
			d = true;
		}
		return d;
	}
}

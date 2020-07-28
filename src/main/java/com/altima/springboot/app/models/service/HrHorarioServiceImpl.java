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
		return em.createQuery("from HrHorario ORDER BY fechaCreacion DESC").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public 	List<HrHorario> obtenerHorario(Long id){
		return em.createNativeQuery("SELECT\n" +
		"	h.id_horario,\n" +
		"	h.hora_inicial,\n" +
		"	h.hora_final,\n" +
		"	h.inicio_comida,\n" +
		"	h.final_comida,\n" +
		"	h.estatus \n" +
		"FROM\n" +
		"	alt_hr_horario h \n" +
		"WHERE\n" +
		"	h.id_horario = " + id).getResultList();
	}
}

package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.HrCalendario;
import com.altima.springboot.app.repository.HrCalendariosRepository;

@Service
public class HrCalendariosServiceImpl implements IHrCalendariosService {
	@Autowired
	HrCalendariosRepository repository;
	@Autowired
	EntityManager em;

	@Override
	@Transactional
	public List<HrCalendario> findAll() {
		// TODO Auto-generated method stub
		return (List<HrCalendario>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrCalendario hrcalendarios) {
		// TODO Auto-generated method stub
		repository.save(hrcalendarios);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrCalendario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrCalendario> findAllCalendarios() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT * from alt_hr_calendario cl ORDER BY cl.fecha_creacion DESC")
				.getResultList();
	}
}

package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrSolicitud;
import com.altima.springboot.app.repository.HrSolicitudRepository;


@Service
public class HrSolicitudServiceImpl implements IHrSolicitudService {
	@Autowired
	private HrSolicitudRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<HrSolicitud> findAll() {
		// TODO Auto-generated method stub
		return (List<HrSolicitud>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrSolicitud hrsolicitud) {
		// TODO Auto-generated method stub
		repository.save(hrsolicitud);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrSolicitud findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

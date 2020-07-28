package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrSolicitudUsuario;
import com.altima.springboot.app.repository.HrSolicitudUsuarioRepository;


@Service
public class HrSolicitudUsuarioServiceImpl implements IHrSolicitudUsuarioService {
	@Autowired
	private HrSolicitudUsuarioRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<HrSolicitudUsuario> findAll() {
		// TODO Auto-generated method stub
		return (List<HrSolicitudUsuario>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrSolicitudUsuario hrsolicitudusuario) {
		// TODO Auto-generated method stub
		repository.save(hrsolicitudusuario);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrSolicitudUsuario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}

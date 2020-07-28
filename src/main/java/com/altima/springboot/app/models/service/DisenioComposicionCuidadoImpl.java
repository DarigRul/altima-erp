package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioComposicionIcuidado;
import com.altima.springboot.app.repository.DisenioComposicionCuidadoRepository;

@Service
public class DisenioComposicionCuidadoImpl implements IDisenioComposicionCuidadoService {
	@Autowired
	private EntityManager em;
	@Autowired
	private DisenioComposicionCuidadoRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioComposicionIcuidado> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioComposicionIcuidado>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioComposicionIcuidado diseniocomposicioncuidado) {
		// TODO Auto-generated method stub
		repository.save(diseniocomposicioncuidado);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioComposicionIcuidado findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> composicioncuidado(Long id_composicion) {
		
		return em.createNativeQuery("call alt_dis_composicion_cuidados('" + id_composicion + "');").getResultList();
		
	}
	
}

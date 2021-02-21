package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.MaquilaControlPedidoBulto;
import com.altima.springboot.app.repository.MaquilaControlPedidoBultoRepository;

@Service
public class MaquilaControlPedidoBultoServiceImpl implements IMaquilaControlPedidoBultoService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	MaquilaControlPedidoBultoRepository repository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MaquilaControlPedidoBulto> Listar(Long id){
		
		return em.createQuery("From MaquilaControlPedidoBulto where idControlPedido="+id+"").getResultList();
	}
	
	@Override
	@Transactional
	public void save(MaquilaControlPedidoBulto maquilacontrolpedidobulto) {
		
		repository.save(maquilacontrolpedidobulto);
	}
	
}

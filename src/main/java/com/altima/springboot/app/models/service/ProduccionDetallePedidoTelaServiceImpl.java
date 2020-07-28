package com.altima.springboot.app.models.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ProduccionDetallePedidoTela;
import com.altima.springboot.app.repository.PrudduccionDetallePedidoTelaRepository;


@Service
public class ProduccionDetallePedidoTelaServiceImpl implements IProduccionDetallePedidoTelaService {
	
	@Autowired
	private PrudduccionDetallePedidoTelaRepository repositoryDetalleTela;
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void save(ProduccionDetallePedidoTela objetoT) {
		repositoryDetalleTela.save(objetoT);

	}

}

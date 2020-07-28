package com.altima.springboot.app.models.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ProduccionDetallePedidoForro;
import com.altima.springboot.app.repository.PrudduccionDetallePedidoForroRepository;


@Service
public class ProduccionDetallePedidoForroServiceImpl implements IProduccionDetallePedidoForroService {
	
	
	@Autowired
	private PrudduccionDetallePedidoForroRepository repositoryDetalleForro;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void save(ProduccionDetallePedidoForro objetoF) {
		repositoryDetalleForro.save(objetoF);

	}

}

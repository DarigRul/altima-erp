package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialClienteFactura;

@Service
public class ComercialClienteFacturaImpl  implements IComercialClienteFacturaService{
	@PersistenceContext
	private EntityManager em;
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialClienteFactura> findListaFacturaCliente(long id) {
		return em.createQuery("from ComercialClienteFactura where id_cliente=" + id ).getResultList();
	}

}



package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.repository.ComercialClienteSucursalRepository;

@Service
public class ComercialClienteSucursalServiceImpl implements IComercialClienteSucursalService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ComercialClienteSucursalRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<ComercialClienteSucursal> findAll() {
		// TODO Auto-generated method stub
		return (List<ComercialClienteSucursal>) repository.findAll();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialClienteSucursal> ClienteSucursales(Long id,Long idagente) {
		return em.createQuery("from ComercialClienteSucursal where id_cliente=" + id +" and IdAgente="+idagente+" AND estatus=1 ORDER BY id_text, nombre_sucursal").getResultList();
	}
	
	@Override
	public void save(ComercialClienteSucursal Sucursal) {
		repository.save(Sucursal);
		
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public ComercialClienteSucursal findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialClienteSucursal> findListaSucrusales() {
		
		return em.createNativeQuery(
				" SELECT \r\n" + 
				"id_cliente_sucursal AS ID,\r\n" + 
				"nombre_sucursal AS SUCURSAL \r\n" + 
				"\r\n" + 
				"FROM alt_comercial_cliente_sucursal;")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialClienteSucursal> findListaSucrusalesCliente(long id) {
		return em.createQuery("from ComercialClienteSucursal where id_cliente=" + id ).getResultList();

	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Integer IdScursal(String id) {
		String re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	COUNT( sucur.id_cliente_sucursal ) \n" + 
				"FROM\n" + 
				"	alt_comercial_cliente_sucursal AS sucur \n" + 
				"WHERE\n" + 
				"	1 = 1\n" + 
				"	AND sucur.id_cliente = "+id).getSingleResult().toString();
	
		return Integer.parseInt(re);
		
	}
}

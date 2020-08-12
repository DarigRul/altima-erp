package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComprasProveedorContacto;
import com.altima.springboot.app.repository.ComprasProveedorContactoRepository;

@Service
public class ComprasProveedorContactoServiceImpl implements IComprasProveedorContactoService {

	@Autowired
	private ComprasProveedorContactoRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional
	public void save(ComprasProveedorContacto comprasProveedorContacto) {
		repository.save(comprasProveedorContacto);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComprasProveedorContacto> findAllByProveedor(Long id) {
		return em.createQuery("FROM ComprasProveedorContacto WHERE IdProveedor="+id).getResultList();
	}

	@Override
	public ComprasProveedorContacto findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void deleteInexistentes(List<ComprasProveedorContacto> comprasProveedorContacto) {
		repository.deleteAll(comprasProveedorContacto);
	}
	
	
	
	
	
}

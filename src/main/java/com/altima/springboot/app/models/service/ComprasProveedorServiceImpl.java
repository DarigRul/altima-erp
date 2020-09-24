package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.repository.ComprasProveedorRepository;

@SuppressWarnings("unchecked")
@Service
public class ComprasProveedorServiceImpl implements IComprasProveedorService {
	
	@Autowired
	private ComprasProveedorRepository repository;
	@Autowired
	private EntityManager em;
	

	@Override
	@Transactional
	public ComprasProveedores findOne(Long Id) {

		return repository.findById(Id).orElse(null);
	}

	
	@Override
	@Transactional
	public List<ComprasProveedores> findAll() {

		return (List<ComprasProveedores>) em.createQuery("FROM ComprasProveedores ORDER BY IdProveedor DESC").getResultList();
	}
	@Override
	@Transactional
	public void save(ComprasProveedores comprasProveedores) {
		
		repository.save(comprasProveedores);
	}

	@Override
	 public boolean ValidarNomenclatura(String nomen) {
		
		
		try {
			String re = em.createNativeQuery(""
					+ "SELECT \r\n" + 
					"compras.nombre_proveedor\r\n" + 
					"FROM\r\n" + 
					"alt_compras_proveedor as compras\r\n" + 
					"WHERE \r\n" + 
					"1=1\r\n" + 
					"AND compras.nomenclatura ='"+nomen+"'").getSingleResult().toString();
					
			return true;
			}
			catch(Exception e) {
				
				return false;
			}
	
	}

}

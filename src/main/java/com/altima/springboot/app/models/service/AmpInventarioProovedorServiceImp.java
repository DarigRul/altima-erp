package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AmpInventarioProovedor;
import com.altima.springboot.app.models.entity.AmpInventarioProovedorPrecio;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.repository.AmpInventarioProovedorPrecioRepository;
import com.altima.springboot.app.repository.AmpInventarioProovedorRepository;
@Service
public class AmpInventarioProovedorServiceImp implements IAmpInventarioProovedorService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AmpInventarioProovedorRepository repository;
	
	@Autowired
	private AmpInventarioProovedorPrecioRepository repositoryPrecio;

	@Override
	public List<AmpInventarioProovedor> findAll(Long iduser) {
		// TODO Auto-generated method stub
		return (List<AmpInventarioProovedor>) repository.findAll();
	}

	@Override
	public void save(AmpInventarioProovedor obj) {
		repository.save(obj);


	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public AmpInventarioProovedor findOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> Proveedores() {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	p.id_proveedor,\r\n" + 
				"	p.nombre_proveedor\r\n" + 
				"FROM\r\n" + 
				"	alt_compras_proveedor AS p \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND p.estatus =1").getResultList();
	
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> View(Long id, String tipo) {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	pro.id_inventario_proveedor,\r\n" + 
				"   compras.id_proveedor,\r\n" + 
				"	pro.clave_proveedor,\r\n" + 
				"	compras.id_text,\r\n" + 
				"	compras.nombre_proveedor,\r\n" + 
				"	IFNULL (( SELECT  precio.precio FROM alt_amp_inventario_proovedor_precio AS precio WHERE precio.id_inventario_proveedor = pro.id_inventario_proveedor ORDER BY precio.id_inventario_proveedor_precio DESC LIMIT 1  ) ,\"S/N\"),\r\n" + 
				"	pro.dias \r\n" + 
				"FROM\r\n" + 
				"	alt_amp_inventario_proovedor AS pro,\r\n" + 
				"	alt_compras_proveedor AS compras \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND pro.id_proveedor = compras.id_proveedor \r\n" + 
				"	AND pro.id_inventario ="+id+"\r\n" + 
				"	AND pro.estatus=1\r\n" + 
				"	AND pro.tipo='"+tipo+"'").getResultList();
	
		return re;
	}



	@Override
	public void savePrecio(AmpInventarioProovedorPrecio obj) {
		repositoryPrecio.save(obj);
	}
	
	@Override
	@Transactional
	public Float findOnePrecio(Long id) {
	
		try {
			
			String re = em
					.createNativeQuery("SELECT\r\n" + 
							"	precio.precio \r\n" + 
							"FROM\r\n" + 
							"	alt_amp_inventario_proovedor_precio AS precio,\r\n" + 
							"	alt_amp_inventario_proovedor AS pro \r\n" + 
							"WHERE\r\n" + 
							"	precio.id_inventario_proveedor = pro.id_inventario_proveedor \r\n" + 
							"	AND precio.id_inventario_proveedor="+id+"\r\n" + 
							"ORDER BY\r\n" + 
							"	precio.id_inventario_proveedor_precio DESC \r\n" + 
							"	LIMIT 1")
					.getSingleResult().toString();
			return Float.parseFloat(re);
			}
			catch(Exception e) {
				
				return null;
			}
		
		 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> ViewPagos(Long id) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	DATE_FORMAT( precio.fecha_creacion, \"%d/%m/%Y\" ),\r\n" + 
				"	CONCAT('$',precio.precio ) \r\n" + 
				"FROM\r\n" + 
				"	alt_amp_inventario_proovedor_precio AS precio,\r\n" + 
				"	alt_amp_inventario_proovedor AS pro \r\n" + 
				"WHERE\r\n" + 
				"	precio.id_inventario_proveedor = pro.id_inventario_proveedor \r\n" + 
				"	AND pro.id_inventario_proveedor = "+id).getResultList();
	
		return re;
	}
}

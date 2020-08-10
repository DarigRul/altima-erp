package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialBordado;
import com.altima.springboot.app.models.entity.ComercialBordadoParteBordado;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.ComercialBordadoParteBordadoRepository;
import com.altima.springboot.app.repository.ComercialBordadoRepository;

@Service
public class ComercialBordadoServiceImpl implements ComercialBordadoService {
	@Autowired
	private EntityManager em;
	
	
	@Autowired
	ComercialBordadoRepository repository;
	
	@Autowired
	ComercialBordadoParteBordadoRepository repositoryParteBordado;
	

	
	
	@Override
	public void save(ComercialBordado Bordado) {
		repository.save(Bordado);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}
	
	@Override
	public ComercialBordado findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(ComercialBordado.class, id);
	}
	
	
	
	@Override
	public void saveParte(ComercialBordadoParteBordado ParteBordado) {
		repositoryParteBordado.save(ParteBordado);
	}
	
	@Override
	@Transactional
	public void deleteParteBordado(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}
	
	@Override
	public ComercialBordadoParteBordado findOneParteBordado(Long id) {
		// TODO Auto-generated method stub
		return em.find(ComercialBordadoParteBordado.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialCliente> findListaClientes(String creado) {
		
		return em.createQuery(
				" FROM ComercialCliente WHERE CcreadoPor= '"+creado+"' and   estatus=1 order by nombre")
				.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialLookup> findListaLookupComercial() {
		
		return em.createQuery(
				" FROM ComercialLookup WHERE Estatus=1 and tipoLookup='Bordado' order by nombreLookup")
				.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findListaBordados() {
		
		return em.createNativeQuery("SELECT\r\n" + 
				"cli.nombre as nombre,\r\n" + 
				"bor.descripcion as descrip,\r\n" + 
				"bor.tamaño as tamaño,\r\n" + 
				"bor.precio as precio,\r\n" + 
				"bor.estatus_bordado as estatus,\r\n" + 
				"bor.id_bordado\r\n" + 
				"\r\n" + 
				"FROM alt_comercial_bordado bor INNER JOIN alt_comercial_cliente cli\r\n" + 
				"ON bor.id_cliente=cli.id_cliente WHERE bor.estatus=\"1\"")
				.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findListaParteBordados(Long id) {
		
		return em.createNativeQuery("SELECT\r\n" + 
				"pt.partes_bordado as parte,\r\n" + 
				"pt.numero_hilo as hilo,\r\n" + 
				"lk.nombre_lookup as color\r\n" + 
				"FROM alt_comercial_bordado_parte_bordado pt INNER JOIN alt_disenio_lookup lk \r\n" + 
				"ON pt.color=lk.id_lookup WHERE pt.estatus=1 AND pt.id_bordado="+ id)
				.getResultList();
	}
	
	

}

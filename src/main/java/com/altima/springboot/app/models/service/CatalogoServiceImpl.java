package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.CatalogoRepository;


@Service
public class CatalogoServiceImpl implements ICatalogoService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CatalogoRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<DisenioLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioLookup>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioLookup diseniolookup) {
		// TODO Auto-generated method stub
		repository.save(diseniolookup);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<DisenioLookup> findAllLookup(String Tipo) {
		return em.createQuery("from DisenioLookup where tipo_lookup='"+Tipo+"'").getResultList();
	}
	
	@Override
	@Transactional
	public DisenioLookup findLastLookupByType(String Tipo){
		return (DisenioLookup) em.createQuery("from DisenioLookup where tipo_lookup='"+Tipo+"' ORDER BY idLookup DESC").setMaxResults(1).getSingleResult();
	}
	 
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<DisenioLookup> result = em.createQuery("from DisenioLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"'").getResultList();
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo,String atributo, String CodigoPrenda,String ruta){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<DisenioLookup> result = em.createQuery("from DisenioLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"' and atributo1='"+atributo+"' and descripcionLookup='"+CodigoPrenda+"' and atributo2=:ruta ").setParameter("ruta", ruta).getResultList();
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}
	
	@Override
	@Transactional
	public boolean findDuplicatePrecioComposicion(Long idPrenda ,Long idFamComposicion){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<DisenioLookup> result = em.createQuery("from DisenioPrecioComposicion where id_prenda="+idPrenda+" and idFamiliaComposicion="+idFamComposicion).getResultList();
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object []> findAllMaterialClasificacion() {
	
		return em.createNativeQuery(""
				+ "SELECT\n" + 
				"	lookup.id_lookup as 'id_lookup',\n" + 
				"	lookup.id_text as 'id_text' ,\n" + 
				"	lookup.nombre_lookup as 'nombre_lookup' ,\n" + 
				"	lookup.atributo_1 as 'atributo_1',\n" + 
				"	lookAMP.nombre_lookup as 'nombre_lookAMP',\n" + 
				"	lookAMP.id_lookup as 'id_lookAMP',\n" + 
				"	lookup.estatus as 'estatus',\n" + 
				"	DATE_FORMAT(lookup.fecha_creacion, '%Y-%m-%d %H:%i:%S'),\n" + 
				"	lookup.creado_por,\n" + 
				"	lookup.actualizado_por,\n" + 
				"	DATE_FORMAT(lookup.ultima_fecha_modificacion, '%Y-%m-%d %H:%i:%S'),\n" + 
				"	lookup.descripcion_lookup\n" +
				//"	lookup.descripcion_lookup as 'descripcionLookup'\n" + 
				"FROM\n" + 
				"	alt_disenio_lookup AS lookup\n" + 
				"	left join alt_amp_lookup AS lookAMP on lookup.atributo_2 = lookAMP.id_lookup AND lookAMP.tipo_lookup = 'Clasificacion' where lookup.tipo_lookup='Material'\n" ).getResultList();
	}

}

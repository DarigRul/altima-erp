package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OrderBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.DisenioLookupRepository;

@Service
public class DisenioLookupServiceImpl implements IDisenioLookupService {
	
	@Autowired
	private EntityManager em;
	@Autowired
	private DisenioLookupRepository repository;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<DisenioLookup> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from alt_disenio_lookup").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<DisenioLookup> findByTipoLookup(String tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipoLookup='" + tipo + "' and Estatus=1").getResultList();
	}
	
	@Override
	@Transactional
	public void save(DisenioLookup lookup) {
		// TODO Auto-generated method stub
		if (lookup.getIdLookup() != null) {
			em.merge(lookup);
		} else {
			em.persist(lookup);
		}
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		em.remove(findOne(id));
	}
	
	@Override
	public DisenioLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(DisenioLookup.class, id);
	}

	@Override
	@Transactional
	public Object findClothesPosition(String prenda) {
		return em.createNativeQuery("select atributo_1 From alt_disenio_lookup where nombre_lookup LIKE '%"+prenda+"%' and tipo_lookup='Familia Prenda' LIMIT 1 ").getSingleResult();
	}

	// @Override
	// public DisenioLookup findOne(Long id) {
	// 	// TODO Auto-generated method stub
	// 	return repository.findById(id).orElse(null);
	// }
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAllByMaquilero(Long idMaquilero) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT adlfprenda.id_lookup, adlfprenda.nombre_lookup, apmf.produccion_maxima FROM alt_disenio_lookup adlfprenda INNER JOIN alt_produccion_maquilador_fprenda apmf ON apmf.id_familia_prenda=adlfprenda.id_lookup WHERE apmf.id_maquilador=:idMaquilero AND adlfprenda.estatus=1").setParameter("idMaquilero", idMaquilero).getResultList();
	}
	
	
}

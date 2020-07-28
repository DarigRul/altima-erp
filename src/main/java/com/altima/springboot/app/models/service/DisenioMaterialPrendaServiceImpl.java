package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioMaterialPrenda;
import com.altima.springboot.app.models.entity.DisenioPrendaCliente;
import com.altima.springboot.app.repository.DisenioMaterialPrendaRepository;

@Service
public class DisenioMaterialPrendaServiceImpl implements IDisenioMaterialPrendaService {
	@Autowired
	private DisenioMaterialPrendaRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioMaterialPrenda> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioMaterialPrenda>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioMaterialPrenda dmp) {
		// TODO Auto-generated method stub
		DisenioMaterialPrenda dmpOld = null;
		
		try {
			dmpOld = (DisenioMaterialPrenda) em.createQuery("FROM DisenioMaterialPrenda WHERE idPrenda = " + dmp.getIdPrenda() + " AND idMaterial = " + dmp.getIdMaterial()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		if(dmpOld != null) {
			dmpOld.setActualizadoPor(dmp.getActualizadoPor());
			dmpOld.setCantidad(dmp.getCantidad());
			dmpOld.setUltimaFechaModificacion(dmp.getUltimaFechaModificacion());
			repository.save(dmpOld);
		}
		else {
			repository.save(dmp);
		}
	}
	
	@Override
	@Transactional
	public void delete(Long[] IdMateriales, Long id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioMaterialPrenda> actuales = (List<DisenioMaterialPrenda>) em.createQuery("FROM DisenioMaterialPrenda WHERE idPrenda = " + id).getResultList();
		
		for(DisenioMaterialPrenda dmp : actuales) {
			int coincidencias = 0;
			for(int i = 0; i < IdMateriales.length; i++) {
				if(dmp.getIdMaterial() == IdMateriales[i]) {
					coincidencias++;
				}
			}
			
			if(coincidencias == 0) {
				repository.deleteById(dmp.getIdMaterialPrenda());
			}
		}
	}
	
	@Override
	@Transactional
	public DisenioMaterialPrenda findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void deleteAllMaterialFromPrenda(Long id) {
		// TODO Auto-generated method stub
		em.createNativeQuery("DELETE FROM alt_disenio_material_prenda WHERE id_prenda =" + id).executeUpdate();
	}
	
}

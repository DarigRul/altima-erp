package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.DisenioFamiliaComposicionTela;
import com.altima.springboot.app.models.entity.DisenioPrendaPatronaje;
import com.altima.springboot.app.repository.DisenioFamiliaComposicionTelaRepository;

@Service
public class DisenioFamiliaComposicionTelaServiceImpl implements IDisenioFamiliaComposicionTelaService {
	
	@Autowired
	private DisenioFamiliaComposicionTelaRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	public List<DisenioFamiliaComposicionTela> findAll() {
		return (List<DisenioFamiliaComposicionTela>) repository.findAll();
		
	}
	
	@Override
	public void save(DisenioFamiliaComposicionTela DisenioFamiliaComposicionTela) {
		DisenioFamiliaComposicionTela dfctOld =  null;
		try {
			dfctOld = (DisenioFamiliaComposicionTela) em.createQuery("FROM DisenioFamiliaComposicionTela WHERE idTela = " + DisenioFamiliaComposicionTela.getIdTela() + " AND idComposicion = " + DisenioFamiliaComposicionTela.getIdFamiliaComposicion()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		if(dfctOld != null) {
			//Ya existe un registro, solo se modificaara
			dfctOld.setActualizadoPor(DisenioFamiliaComposicionTela.getActualizadoPor());
			dfctOld.setUltimaFechaModificacion(DisenioFamiliaComposicionTela.getUltimaFechaModificacion());
			repository.save(dfctOld);
			
		}
		else {
			//Es completamente nuevo, so, se guarda por completo
			repository.save(DisenioFamiliaComposicionTela);
		}	
	}
	
	@Override
	public void deleteBorrados(String[] idComposicion, Long idTela) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioFamiliaComposicionTela> listaActuales = (List<DisenioFamiliaComposicionTela>) em.createQuery("FROM DisenioFamiliaComposicionTela WHERE idTela = " + idTela).getResultList();
		
		for(DisenioFamiliaComposicionTela dfct : listaActuales){
			int Coincidencias = 0;
			for(int i = 0; i < (idComposicion.length - 1); i++) {
				if(dfct.getIdFamiliaComposicion().longValue() == Long.valueOf(idComposicion[i])) {
					Coincidencias++;
				}
			}
			
			if(Coincidencias == 0) {
				repository.deleteById(dfct.getIdFamiliaComposicionTela());
			}
		}	
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
		
	}
	
	@Override
	public DisenioFamiliaComposicionTela findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
}

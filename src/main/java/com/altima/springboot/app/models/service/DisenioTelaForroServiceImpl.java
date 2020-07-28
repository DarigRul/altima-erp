package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioMaterialTela;
import com.altima.springboot.app.models.entity.DisenioTelaForro;
import com.altima.springboot.app.repository.DisenioTelaForroRepository;


@Service
public class DisenioTelaForroServiceImpl implements IDisenioTelaForroService {
	@Autowired
	private DisenioTelaForroRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public List<DisenioTelaForro> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioTelaForro>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioTelaForro diseniotelaforro) {
		// TODO Auto-generated method stub
		DisenioTelaForro dtOld = null;
		try {
			dtOld = (DisenioTelaForro) em.createQuery("FROM DisenioTelaForro WHERE idTela = " + diseniotelaforro.getIdTela() + " AND idForro = " + diseniotelaforro.getIdForro()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		if(dtOld != null) {
			//Ya existe un registro, solo se actualiza
			dtOld.setActualizadoPor(diseniotelaforro.getActualizadoPor());
			dtOld.setUltimaFechaModificacion(diseniotelaforro.getUltimaFechaModificacion());
			repository.save(dtOld);
		}
		else {
			//No existe un registro, se guarda todo completo.
			repository.save(diseniotelaforro);
		}
	}
	
	@Override
	public void deleteEliminados(String[] idForros, Long idTela) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioTelaForro> listaActuales = (List<DisenioTelaForro>) em.createQuery("FROM DisenioTelaForro WHERE idTela = " + idTela).getResultList();
		
		for(DisenioTelaForro dmt : listaActuales){
			int Coincidencias = 0;
			for(int i = 0; i < (idForros.length); i++) {
				if(dmt.getIdForro().toString().equalsIgnoreCase(idForros[i].toString())) {
					Coincidencias++;
				}
			}
			
			if(Coincidencias == 0) {
				repository.deleteById(dmt.getIdTelaForro());
			}
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioTelaForro findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
}

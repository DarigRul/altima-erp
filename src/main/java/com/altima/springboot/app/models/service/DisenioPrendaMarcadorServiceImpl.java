package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.altima.springboot.app.models.entity.DisenioPrendaCliente;
import com.altima.springboot.app.models.entity.DisenioPrendaMarcador;
import com.altima.springboot.app.repository.DisenioPrendaMarcadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisenioPrendaMarcadorServiceImpl implements IDisenioPrendaMarcadorService {
	@Autowired
	private DisenioPrendaMarcadorRepository repository;
	@Autowired
	EntityManager em;
	
	@Override
	public List<DisenioPrendaMarcador> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioPrendaMarcador>) repository.findAll();
	}
	
	@Override
	public void save(DisenioPrendaMarcador dpm) {
		// TODO Auto-generated method stub
		DisenioPrendaMarcador dpmOld = null;
		try {
			dpmOld = (DisenioPrendaMarcador) em.createQuery("FROM DisenioPrendaMarcador WHERE idPrenda = " + dpm.getIdPrenda() + " AND idMarcador = " + dpm.getIdMarcador()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		//repository.save(disenioPrendaCliente);
		if(dpmOld != null) {
			//Ya existe un registro asi, solo se modificara.
			dpmOld.setActualizadoPor(dpm.getActualizadoPor());
			dpmOld.setUltimaFechaModificacion(dpm.getUltimaFechaModificacion());
			repository.save(dpmOld);		
		}
		else {
			repository.save(dpm);
		}
	}
	
	@Override
	public void delete(Long[] idMarcadores, Long id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioPrendaMarcador> listaActuales = (List<DisenioPrendaMarcador>) em.createQuery("FROM DisenioPrendaMarcador WHERE idPrenda = " + id).getResultList();
		
		for(DisenioPrendaMarcador dpm : listaActuales){
			int Coincidencias = 0;
			for(int i = 0; i < idMarcadores.length; i++) {
				if(dpm.getIdMarcador().longValue() == idMarcadores[i]) {
					Coincidencias++;
				}
			}
			
			if(Coincidencias == 0) {
				repository.deleteById(dpm.getIdPrendaMarcador());
			}
		}
	}
	
	@Override
	public DisenioPrendaMarcador findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioPrendaMarcador> findByIdPrenda(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery(
				"SELECT dpm.idMarcador,dl.nombreLookup as idMarcador,dpm.idPrenda FROM DisenioPrendaMarcador dpm inner join DisenioLookup dl on dpm.idMarcador=dl.idLookup WHERE dpm.idPrenda ="
						+ id)
				.getResultList();
	}
	
	@Override
	@Transactional
	public void deleteByIdPrenda(Long id) {
		// TODO Auto-generated method stub
		em.createNativeQuery("DELETE FROM alt_disenio_prenda_marcador WHERE id_prenda = " + id).executeUpdate();
		;
	}
	
}
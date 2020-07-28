package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioPrendaCliente;
import com.altima.springboot.app.models.entity.DisenioPrendaPatronaje;
import com.altima.springboot.app.repository.DisenioPrendaPatronajeRepository;

@Service
public class DisenioPrendaPatronajeServiceImpl implements IDisenioPrendaPatronajeService {
	@Autowired
	private DisenioPrendaPatronajeRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	public List<DisenioPrendaPatronaje> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void save(DisenioPrendaPatronaje dpp) {
		// TODO Auto-generated method stub
		DisenioPrendaPatronaje dppOld = null;
		try {
			dppOld = (DisenioPrendaPatronaje) em.createQuery("FROM DisenioPrendaPatronaje WHERE idPrenda = " + dpp.getIdPrenda() + " AND idPatronaje = " + dpp.getIdPatronaje()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		if(dppOld != null) {
			//Ya existe un registro asi, solo se modificara.
			dppOld.setCantidadEntretela(dpp.getCantidadEntretela());
			dppOld.setCantidadForro(dpp.getCantidadForro());
			dppOld.setCantidadForroSecundaria(dpp.getCantidadForroSecundaria());
			dppOld.setCantidadTela(dpp.getCantidadTela());
			dppOld.setCantidadTelaSecundaria(dpp.getCantidadTelaSecundaria());
			dppOld.setActualizadoPor(dpp.getActualizadoPor());
			dppOld.setUltimaFechaModificacion(dpp.getUltimaFechaModificacion());
			repository.save(dppOld);
			
		}
		else {
			repository.save(dpp);
		}		
	}
	
	@Override
	public void delete(Long[] idPatronajes, Long id) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioPrendaPatronaje> listaActuales = (List<DisenioPrendaPatronaje>) em.createQuery("FROM DisenioPrendaPatronaje WHERE idPrenda = " + id).getResultList();
		
		for(DisenioPrendaPatronaje dpp : listaActuales){
			int Coincidencias = 0;
			for(int i = 0; i < idPatronajes.length; i++) {
				if(dpp.getIdPatronaje().equalsIgnoreCase(idPatronajes[i].toString())) {
					Coincidencias++;
				}
			}
			
			if(Coincidencias == 0) {
				repository.deleteById(dpp.getIdPrendaPatronaje());
			}
		}		
	}
	
	@Override
	@Transactional
	public void deleteAllPatronajeFromPrenda(Long id) {
		// TODO Auto-generated method stub
		em.createNativeQuery("DELETE FROM alt_disenio_prenda_patronaje WHERE id_prenda = " + id).executeUpdate();
	}
	
}

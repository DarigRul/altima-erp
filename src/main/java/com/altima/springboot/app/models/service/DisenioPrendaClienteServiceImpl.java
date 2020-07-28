package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.DisenioPrendaCliente;
import com.altima.springboot.app.repository.DisenioPrendaClienteRepository;

@Service
public class DisenioPrendaClienteServiceImpl implements IDisenioPrendaClienteService 
{
	@Autowired
	private DisenioPrendaClienteRepository repository;
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioPrendaCliente> findAllByPrenda(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("FROM DisenioPrendaCliente WHERE idPrenda = " + id).getResultList();
	}

	@Override
	public void save(DisenioPrendaCliente dpc) {
		
		DisenioPrendaCliente dpcOld = null;
		try {
			 dpcOld = (DisenioPrendaCliente) em.createQuery("FROM DisenioPrendaCliente WHERE idPrenda = " + dpc.getIdPrenda() + " AND idCliente = " + dpc.getIdCliente()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		//repository.save(disenioPrendaCliente);
		if(dpcOld != null) {
			//Ya existe un registro asi, solo se modificara.
			dpcOld.setIdCliente(dpc.getIdCliente());
			dpcOld.setActualizadoPor(dpc.getActualizadoPor());
			dpcOld.setUltimaFechaModificacion(dpc.getUltimaFechaModificacion());
			repository.save(dpcOld);		
		}
		else {
			repository.save(dpc);
		}	
	}

	@Override
	public void delete(Long[] idClientes, Long idPrenda) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioPrendaCliente> listaActuales = (List<DisenioPrendaCliente>) em.createQuery("FROM DisenioPrendaCliente WHERE idPrenda = " + idPrenda).getResultList();
		
		for(DisenioPrendaCliente dpc : listaActuales){
			int Coincidencias = 0;
			for(int i = 0; i < idClientes.length; i++) {
				if(dpc.getIdCliente() == idClientes[i]) {
					Coincidencias++;
				}
			}
			
			if(Coincidencias == 0) {
				repository.deleteById(dpc.getIdPrendaCliente());
			}
		}
	}

}

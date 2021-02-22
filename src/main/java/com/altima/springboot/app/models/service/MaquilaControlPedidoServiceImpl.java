package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.repository.MaquilaControlPedidoRepository;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.MaquilaControlPedido;


@Service
public class MaquilaControlPedidoServiceImpl implements IMaquilaControlPedidoService{

	@Autowired
	MaquilaControlPedidoRepository repository;
	
	@PersistenceContext
	EntityManager em;
	
	
	@Override
	public void save(MaquilaControlPedido maquilacontrol) {
		repository.save(maquilacontrol);
		
	}
	
	@Override
	@Transactional
	public MaquilaControlPedido findOne(Long id) {
	return repository.findById(id).orElse(null);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MaquilaControlPedido> findAllMaquilaControlPedido(){
		
		return em.createQuery("From MaquilaControlPedido where estatus=1").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialCliente> findAllCliente(){
		
		return em.createQuery("From ComercialCliente where estatus=1").getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllPrendaModelo(){
		
		return em.createNativeQuery("select adp.id_text,adl.nombre_lookup,adp.id_prenda from alt_disenio_prenda adp,alt_disenio_lookup adl where adp.id_familia_prenda=adl.id_lookup and adp.estatus=1 and adl.estatus=1").getResultList();
	}
	
	
	 
	

}

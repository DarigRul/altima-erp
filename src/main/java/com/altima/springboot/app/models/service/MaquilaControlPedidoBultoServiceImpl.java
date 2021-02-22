package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.MaquilaControlPedidoBulto;
import com.altima.springboot.app.repository.MaquilaControlPedidoBultoRepository;

@Service
public class MaquilaControlPedidoBultoServiceImpl implements IMaquilaControlPedidoBultoService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	MaquilaControlPedidoBultoRepository repository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MaquilaControlPedidoBulto> Listar(Long id){
		
		return em.createQuery("From MaquilaControlPedidoBulto where idControlPedido="+id+"").getResultList();
	}
	
	@Override
	@Transactional
	public void save(MaquilaControlPedidoBulto maquilacontrolpedidobulto) {
		
		repository.save(maquilacontrolpedidobulto);
	}

	@Override
	@Transactional
	public String ContarOperaciones(String idprenda) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("Select count(PO.id_asignacion)\r\n"
				+ "\r\n"
				+ "FROM\r\n"
				+ "	alt_maquila_prenda_operacion AS PO\r\n"
				+ "	INNER JOIN alt_maquila_lookup operacion ON operacion.id_lookup = PO.id_operacion\r\n"
				+ "	INNER JOIN alt_maquila_lookup familia ON operacion.descripcion_lookup = familia.id_lookup\r\n"
				+ "	WHERE \r\n"
				+ "	1=1\r\n"
				+ "	AND PO.id_prenda="+idprenda+"").getSingleResult().toString();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated  stub
		repository.deleteById(id);
		
	}
	
}

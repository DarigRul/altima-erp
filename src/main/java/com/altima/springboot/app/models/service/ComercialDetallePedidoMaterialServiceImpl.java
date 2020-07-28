package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoMaterial;
import com.altima.springboot.app.repository.ComercialClienteEmpleadoRepository;
import com.altima.springboot.app.repository.ProduccionDetallePedidoMaterialRepository;

@Service
public class ComercialDetallePedidoMaterialServiceImpl implements IProduccionDetallePedidoMaterialService {
	
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ProduccionDetallePedidoMaterialRepository repository;
	
	
	@Override
	public void save(ProduccionDetallePedidoMaterial clienteempleado) {
		// TODO Auto-generated method stub
		repository.save(clienteempleado);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProduccionDetallePedidoMaterial findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
		/*return null;*/
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionDetallePedidoMaterial> findAllMaterial(Long id ) {
		
		return em.createQuery("from ProduccionDetallePedidoMaterial  where idDetallePedido = "+id).getResultList();
		//return (List<ComercialCoordinadoMaterial>) repositoryMaterial.findAll();
	}
	

}

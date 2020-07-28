package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.altima.springboot.app.models.entity.ProduccionPedidoColeccion;
import com.altima.springboot.app.repository.ProduccionPedidoColeccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProduccionPedidoColeccionServiceImpl implements IProduccionPedidoColeccionService {
    @PersistenceContext
	private EntityManager em;
	@Autowired
	private ProduccionPedidoColeccionRepository repository;
    @Override
    @Transactional
	public List<ProduccionPedidoColeccion> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionPedidoColeccion>) repository.findAll();
	}
    @Override
    @Transactional
	public void save(ProduccionPedidoColeccion coleccion) {
		// TODO Auto-generated method stub
		repository.save(coleccion);
	}
    @Override
    @Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
    @Override
    @Transactional
	public ProduccionPedidoColeccion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
    }
    @Override
    @Transactional
	public List<Object[]> findAllDetail(Long idPedido) {
		// TODO Auto-generated method stub
		return em.createQuery("Select ppc.idProduccionPedidoColeccion,ppc.cantidad,dl.nombreLookup,dl2.nombreLookup,pp.idPedido From ProduccionPedidoColeccion ppc Inner Join DisenioLookup dl on ppc.idFamiliaPrenda=dl.idLookup Inner Join DisenioLookup dl2 on ppc.idFamiliaGenero=dl2.idLookup Inner Join ProduccionPedido pp on ppc.idPedido=pp.idPedido Where pp.idPedido="+idPedido).getResultList();
	}
    
}
package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialMovimientoMuestraDetalle;
import com.altima.springboot.app.repository.ComercialMovimientoMuestraDetalleRepository;

@Service
public class ComercialMovimientoMuestraDetalleServiceImpl implements IComercialMovimientoMuestraDetalleService {

	@Autowired
	private ComercialMovimientoMuestraDetalleRepository repository;
	 
	@Autowired
	private EntityManager em;
	
	
	@Override
	@Transactional
	public void save(ComercialMovimientoMuestraDetalle muestraDetalle) {

		repository.save(muestraDetalle);
		
	}
	
	@Override
	@Transactional
	public ComercialMovimientoMuestraDetalle findOne(Long id) {
		
		
		return em.find(ComercialMovimientoMuestraDetalle.class, id);
	}
	
	@Override
	@Transactional
	public void removeEntities(List<ComercialMovimientoMuestraDetalle> Entities) {
		
		
		repository.deleteAll(Entities);
	}

	@Override
	@Transactional
	public List<ComercialMovimientoMuestraDetalle> findAll() {
		
		return (List<ComercialMovimientoMuestraDetalle>) repository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialMovimientoMuestraDetalle> findAllbyMovimiento(Long id){
		
		return em.createQuery("FROM ComercialMovimientoMuestraDetalle WHERE idMovimiento="+id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialMovimientoMuestraDetalle> findAllbyMovimientoEstatus(Long id){
		
		return em.createNativeQuery("select muest.id_movimiento_muestra_detalle, \r\n" + 
									"		muest.id_detalle_pedido, \r\n" + 
									"		muest.codigo_barras, \r\n" + 
									"		muest.id_movimiento, \r\n" + 
									"		muest.nombre_muestra, \r\n" + 
									"		prenda.id_text as modelo_prenda,\r\n" + 
									"		tela.id_text as codigo_tela,\r\n" + 
									"		muest.fecha_salida,\r\n" + 
									"		muest.entregada_por,\r\n" + 
									"		precio.precio_muestrario,\r\n" + 
									"		muest.recibida_por,\r\n" + 
									"		muest.recargos,\r\n" + 
									"		muest.estatus\r\n" + 
									"from alt_comercial_movimiento_muestra_detalle as muest\r\n" + 
									"INNER JOIN alt_disenio_prenda prenda ON muest.modelo_prenda = prenda.id_prenda\r\n" + 
									"INNER JOIN alt_disenio_lista_precio_prenda precio ON prenda.id_prenda = precio.id_prenda\r\n" + 
									"INNER JOIN alt_disenio_tela tela ON muest.codigo_tela = tela.id_tela\r\n" + 
									"\r\n" + 
									"where muest.id_movimiento = "+id+" \r\n" + 
									"and (muest.estatus =4 \r\n" + 
									"	 or muest.estatus=6 \r\n" + 
									"	 or muest.estatus = 8 \r\n" + 
									"    or muest.estatus = 11 \r\n" +
									"    or muest.estatus = 3 \r\n" +
									"    or muest.estatus = 2 \r\n" +
									"	 or IF((SELECT COUNT(*) FROM alt_comercial_movimiento_muestra_detalle\r\n" + 
									"WHERE id_movimiento = muest.id_movimiento\r\n" + 
									"AND id_detalle_pedido =muest.id_detalle_pedido\r\n" + 
									"AND (estatus !=1))=0,\r\n" + 
									"muest.estatus=1, null) )").getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialMovimientoMuestraDetalle> findAllbyEstatus(Long id){
		
		return em.createQuery("FROM ComercialMovimientoMuestraDetalle WHERE idMovimiento="+id+" AND (estatus = 4 OR estatus = 6 OR estatus = 8)").getResultList();
	}
	
	@Override
	@Transactional
	public String ifExistCheckBox(Long id) {
		
		return em.createNativeQuery("SELECT IF((SELECT COUNT(*) FROM alt_comercial_movimiento_muestra_detalle WHERE id_movimiento ="+id+" AND (estatus = 4 OR estatus = 6 OR estatus = 8))>0, 1 , 0)").getSingleResult().toString();
	}
	
	@Override
	@Transactional
	public ComercialMovimientoMuestraDetalle FindMuestraByPedido(Long id, Long idDetalle) {
		
		return (ComercialMovimientoMuestraDetalle) em.createQuery("FROM ComercialMovimientoMuestraDetalle WHERE idMovimiento="+id+" AND idDetallePedido="+idDetalle+" AND (estatus = 4 OR estatus = 6 OR estatus = 8)").getSingleResult();
	}
}

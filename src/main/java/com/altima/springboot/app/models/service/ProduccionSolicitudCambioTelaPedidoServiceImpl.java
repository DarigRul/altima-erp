package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ProduccionSolicitudCambioTelaPedido;
import com.altima.springboot.app.repository.ProduccionSolicitudCambioTelaPedidoRepository;
@Service
public class ProduccionSolicitudCambioTelaPedidoServiceImpl implements IProduccionSolicitudCambioTelaPedidoService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ProduccionSolicitudCambioTelaPedidoRepository repository;
	@Override
	public List<ProduccionSolicitudCambioTelaPedido> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionSolicitudCambioTelaPedido>) repository.findAll();
	}

	@Override
	public void save(ProduccionSolicitudCambioTelaPedido obj) {
		// TODO Auto-generated method stub
		repository.save(obj);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public ProduccionSolicitudCambioTelaPedido findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> pedidosCerrados() {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	pedido.id_pedido_informacion,\r\n" + 
				"	pedido.id_text \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_pedido_informacion AS pedido \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND pedido.estatus = 2 \r\n" + 
				"ORDER BY\r\n" + 
				"	pedido.id_text ASC").getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> infPedido(Long id) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT \r\n" + 
				"cliente.nombre,\r\n" + 
				"	DATE_FORMAT(pedido.fecha_entrega, '%Y-%m-%d %T' )\n" + 
				"FROM \r\n" + 
				"alt_comercial_pedido_informacion as pedido ,\r\n" + 
				"alt_comercial_cliente as cliente\r\n" + 
				"WHERE\r\n" + 
				"1=1\r\n" + 
				"AND cliente.id_cliente = pedido.id_empresa\r\n" + 
				"AND pedido.id_pedido_informacion="+id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> View() {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	solicitud.id_tela_pedido,\r\n" + 
				"	solicitud.id_text AS idSolicitud,\r\n" + 
				"	solicitud.creado_por,\r\n" + 
				"	solicitud.fecha_creacion,\r\n" + 
				"	cliente.nombre,\r\n" + 
				"	pedido.id_text AS idPeido,\r\n" + 
				"	pedido.fecha_entrega,\r\n" + 
				"IF\r\n" + 
				"	( solicitud.estatus_envio = 0, 'En espera', 'Enviado' ),\r\n" + 
				"	pedido.id_pedido_informacion \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_solicitud_cambio_tela_pedido AS solicitud,\r\n" + 
				"	alt_comercial_cliente AS cliente,\r\n" + 
				"	alt_comercial_pedido_informacion AS pedido \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND pedido.id_pedido_informacion = solicitud.id_pedido \r\n" + 
				"	AND pedido.id_empresa = cliente.id_cliente").getResultList();
		return re;
	}

	

}

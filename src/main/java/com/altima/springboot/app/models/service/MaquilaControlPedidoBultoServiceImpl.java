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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> GenerarTickets(String idcontrol, String idprenda) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT \r\n"
				+ " amcp.id_control_pedido,\r\n"
				+ "PO.id_prenda,\r\n"
				+ "amcp.pedido,\r\n"
				+ "amcp.modelo,\r\n"
				+ "amcpb.cantidad_prenda_bulto,\r\n"
				+ "familia.nombre_lookup as familia,\r\n"
				+ "operacion.nombre_lookup as operacion,\r\n"
				+ "operacion.atributo_2 as sam,\r\n"
				+ "ROUND((60/ operacion.atributo_2)) as hrs,\r\n"
				+ "ROUND(((60/ operacion.atributo_2)* operacion.atributo_3)) as turno,round(amcpb.cantidad_prenda_bulto*operacion.atributo_2,2) \r\n"
				+ "FROM\r\n"
				+ "	alt_maquila_prenda_operacion AS PO\r\n"
				+ "	INNER JOIN alt_maquila_lookup operacion ON operacion.id_lookup = PO.id_operacion\r\n"
				+ "	INNER JOIN alt_maquila_lookup familia ON operacion.descripcion_lookup = familia.id_lookup\r\n"
				+ "	INNER JOIN alt_maquila_control_pedidos as amcp\r\n"
				+ "INNER JOIN alt_maquila_control_pedidos_bulto as amcpb	on amcpb.id_control_pedido=amcp.id_control_pedido\r\n"
				+ "	WHERE \r\n"
				+ "	1=1\r\n"
				+ "	and PO.id_prenda=amcp.id_prenda\r\n"
				+ "	AND PO.id_prenda="+idprenda+"\r\n"
				+ "	AND amcp.id_control_pedido="+idcontrol+"").getResultList();
	}

	@Override
	@Transactional
	public Double SumatoriaCantidad(Long id) {
		// TODO Auto-generated method stub
		return  (Double) em.createNativeQuery("SELECT sum(cantidad_prenda_bulto) FROM `alt_maquila_control_pedidos_bulto` where id_control_pedido="+id+"").getSingleResult();
	}
	
}

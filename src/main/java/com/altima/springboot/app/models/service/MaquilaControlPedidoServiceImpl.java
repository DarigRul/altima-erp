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
	public List<Object[]> findAllMaquilaControlPedido(){
		
		return em.createNativeQuery("SELECT control_pedidos.*,\r\n"
				+ "       control_pedidos.cantidad / ( Count(operaciones.id_prenda) )\r\n"
				+ "FROM   (SELECT amcp.*,\r\n"
				+ "               ( Sum(amat.cantidad_prenda_bulto) ) AS cantidad\r\n"
				+ "        FROM   `alt_maquila_control_pedidos` amcp\r\n"
				+ "               LEFT JOIN alt_maquila_asignacion_tickets amat\r\n"
				+ "                      ON amcp.id_control_pedido = amat.id_control_pedido\r\n"
				+ "        WHERE  amcp.estatus = 1\r\n"
				+ "        GROUP  BY id_control_pedido) AS control_pedidos\r\n"
				+ "       LEFT JOIN (SELECT PO.id_prenda\r\n"
				+ "                  FROM   alt_maquila_prenda_operacion AS PO) AS operaciones\r\n"
				+ "              ON control_pedidos.id_prenda = operaciones.id_prenda\r\n"
				+ "GROUP  BY id_control_pedido ").getResultList();
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

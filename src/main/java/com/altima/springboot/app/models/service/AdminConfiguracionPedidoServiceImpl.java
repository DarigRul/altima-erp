package com.altima.springboot.app.models.service;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.repository.AdminConfiguracionPedidoRepository;

@Service
public class AdminConfiguracionPedidoServiceImpl implements IAdminConfiguracionPedidoService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AdminConfiguracionPedidoRepository repository;
	
	@Override
	public List<AdminConfiguracionPedido> findAll() {
		// TODO Auto-generated method stub
		return (List<AdminConfiguracionPedido>) repository.findAll();
	}

	@Override
	public void save(AdminConfiguracionPedido config) {
		repository.save(config);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public AdminConfiguracionPedido findOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> pedidos( ) {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	inf.id_pedido_informacion,\r\n" + 
				"	CONCAT( inf.id_text, '-', cliente.nombre )  \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_pedido_informacion AS inf,\r\n" + 
				"	alt_comercial_cliente AS cliente \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND cliente.id_cliente = inf.id_empresa\r\n" + 
				"	ORDER BY inf.id_text DESC").getResultList();
		return re;
		//AND material.nombre_material NOT IN ('Tela principal')
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllView( ) {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"c.id_configuracion_pedido,\r\n" + 
				"CASE\r\n" + 
				"	WHEN c.tipo_pedido = '1' THEN 'Pedido General' \r\n" + 
				"	WHEN c.tipo_pedido = '2' THEN 'Pedido Especial' \r\n" + 
				"	WHEN c.tipo_pedido = '3' THEN 'Stock' \r\n" + 
				"	WHEN c.tipo_pedido = '4' THEN 'Resurtido' \r\n" + 
				"	WHEN c.tipo_pedido = '5' THEN 'Reajustes' \r\n" + 
				"	ELSE 'N/P' \r\n" + 
				"END AS tipo,\r\n" + 
				"c.estatus\r\n" + 
				"FROM\r\n" + 
				"	alt_admin_configuracion_pedido AS c").getResultList();
		return re;
		//AND material.nombre_material NOT IN ('Tela principal')
	}
	
	@Override
	public  boolean validarPedido (Long id ) {
		try {
			String re = em.createNativeQuery(""
					+ "SELECT\r\n" + 
					"	con.tipo_pedido \r\n" + 
					"FROM\r\n" + 
					"	alt_admin_configuracion_pedido AS con \r\n" + 
					"WHERE\r\n" + 
					"	con.tipo_pedido ="+id).getSingleResult().toString();
					System.out.println("regresa el true");
			return true;
			}
			catch(Exception e) {
				System.out.println("regresa el false");
				return false;
			}
	
	}

}

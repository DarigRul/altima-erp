package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.repository.CargaPedidoRepository;

@Service
public class CargaPedidoServiceImpl implements ICargaPedidoService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CargaPedidoRepository repository;

	@Override
	public List<ComercialPedidoInformacion> findAll() {
		// TODO Auto-generated method stub
		return (List<ComercialPedidoInformacion>) repository.findAll();
	}

	@Override
	public void save(ComercialPedidoInformacion pedido) {
		// TODO Auto-generated method stub
		repository.save(pedido);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);

	}

	@Override
	public ComercialPedidoInformacion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> CargaPedidoVista(Long iduser) {
		List<Object[]> re = null;
		if (iduser != null) {
			re = em.createNativeQuery("SELECT\r\n" + "	informacion.id_pedido_informacion,\r\n"
					+ "	informacion.id_text,\r\n" + "	cliente.nombre,\r\n"
					+ "	IFNULL( DATE( informacion.fecha_entrega ), 'Por definir' ),\r\n" + "	cliente.id_cliente \r\n"
					+ "FROM\r\n" + "	alt_comercial_pedido_informacion AS informacion,\r\n"
					+ "	alt_comercial_cliente AS cliente \r\n" + "WHERE\r\n" + "	1 = 1 \r\n"
					+ "	AND informacion.id_empresa = cliente.id_cliente \r\n" + "	AND informacion.id_usuario = "
					+ iduser + " \r\n" + "ORDER BY\r\n" + "	informacion.fecha_creacion DESC").getResultList();
		} else {
			re = em.createNativeQuery("SELECT\r\n" + "	informacion.id_pedido_informacion,\r\n"
					+ "	informacion.id_text,\r\n" + "	cliente.nombre,\r\n"
					+ "	IFNULL( DATE( informacion.fecha_entrega ), 'Por definir' ),\r\n" + "	cliente.id_cliente \r\n"
					+ "FROM\r\n" + "	alt_comercial_pedido_informacion AS informacion,\r\n"
					+ "	alt_comercial_cliente AS cliente \r\n" + "WHERE\r\n" + "	1 = 1 \r\n"
					+ "	AND informacion.id_empresa = cliente.id_cliente \r\n" + "ORDER BY\r\n"
					+ "	informacion.fecha_creacion DESC").getResultList();
		}

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> PedidosExistenteIdEmpresa(Long id) {
		List<Object[]> re = em.createNativeQuery(
				"select informacion.id_pedido_informacion, informacion.id_text  from alt_comercial_pedido_informacion as informacion \r\n"
						+ "where informacion.id_empresa=" + id)
				.getResultList();
		return re;
	}

}

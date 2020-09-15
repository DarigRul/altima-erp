package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialSpfEmpleado;
import com.altima.springboot.app.repository.ComercialSpfEmpleadoRepository;

@Service
public class ComercialSpfEmpleadoServiceImpl implements IComercialSpfEmpleadoService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ComercialSpfEmpleadoRepository repository;

	@Override
	public void save(ComercialSpfEmpleado ComercialSpfEmpleado) {
		// TODO Auto-generated method stub
		repository.save(ComercialSpfEmpleado);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public ComercialSpfEmpleado findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<ComercialSpfEmpleado> findAll() {
		return (List<ComercialSpfEmpleado>) repository.findAll();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> empleados(Long id ) {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	empleado.id_empleado,\r\n" + 
				"	empleado.nombre_empleado \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_pedido_informacion AS pedido,\r\n" + 
				"	alt_comercial_pedido_informacion AS spf,\r\n" + 
				"	alt_comercial_cliente_empleado AS empleado \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND spf.id_pedido = pedido.id_pedido_informacion \r\n" + 
				"	AND empleado.id_pedido_informacion = pedido.id_pedido_informacion \r\n" + 
				"	AND spf.id_pedido_informacion = "+id+" \r\n" + 
				"	AND empleado.nombre_empleado LIKE '%spf%'").getResultList();
		return re;
		//AND material.nombre_material NOT IN ('Tela principal')
	}
}

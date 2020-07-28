package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AmpInventario;
import com.altima.springboot.app.repository.AmpInventarioRepository;

@Service
public class AmpInventarioServiceImpl implements IAmpInventarioService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AmpInventarioRepository repository;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> findAll() {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	inventario.id_inventario,\r\n" + 
				"	inventario.id_text,\r\n" + 
				"	inventario.articulo,\r\n" + 
				"	look_clas.nombre_lookup AS clas,\r\n" + 
				"	look.nombre_lookup AS linea,\r\n" + 
				"	'0',\r\n" + 
				"	look2.nombre_lookup AS medida,"
				+ " inventario.estatus  \r\n" + 
				"FROM\r\n" + 
				"	alt_amp_inventario AS inventario,\r\n" + 
				"	alt_amp_lookup AS look,\r\n" + 
				"	alt_amp_lookup AS look_clas,\r\n" + 
				"	alt_disenio_lookup AS look2 \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND inventario.id_linea = look.id_lookup \r\n" + 
				"	AND inventario.id_unidad_medida = look2.id_lookup \r\n" + 
				"	AND look_clas.id_lookup = look.descripcion_lookup").getResultList();
		return re;
	}

	@Override
	public void save(AmpInventario inventario) {
		repository.save(inventario);


	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public AmpInventario findOne(Long id) {

		return repository.findById(id).orElse(null);
	}

}

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
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	material.id_material,\n" + 
				"	material.id_text,\n" + 
				"	material.nombre_material AS nombre,\n" + 
				"	lookAMP.nombre_lookup AS clasficacion,\n" + 
				"	look.nombre_lookup,\n" + 
				"	'0',\n" + 
				"	look2.nombre_lookup AS medida,\n" + 
				"	material.estatus,\n" + 
				"	'm' \n" + 
				"FROM\n" + 
				"	alt_disenio_material AS material,\n" + 
				"	alt_amp_lookup AS lookAMP,\n" + 
				"	alt_disenio_lookup AS look,\n" + 
				"	alt_disenio_lookup AS look2 \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND material.id_tipo_material = look.id_lookup \n" + 
				"	AND look.atributo_2 = lookAMP.id_lookup \n" + 
				"	AND material.id_unidad_medida = look2.id_lookup \n" + 
				//"	AND material.estatus = 1 \n" + 
				"	AND material.estatus_material = 1 UNION\n" + 
				"SELECT\n" + 
				"	tela.id_tela,\n" + 
				"	tela.id_text,\n" + 
				"	tela.nombre_tela AS nombre,\n" + 
				"	'Materia Prima' AS clasficacion,\n" + 
				"	'Tela',\n" + 
				"	'0',\n" + 
				"	look.nombre_lookup AS medida,\n" + 
				"	tela.estatus,\n" + 
				"	't' \n" + 
				"FROM\n" + 
				"	alt_disenio_tela AS tela,\n" + 
				"	alt_disenio_lookup AS look \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND look.id_lookup = tela.id_unidad_medida \n" + 
				//"	AND tela.estatus = 1 \n" + 
				"	AND tela.estatus_tela = 1 UNION\n" + 
				"SELECT\n" + 
				"	forro.id_forro,\n" + 
				"	forro.id_text,\n" + 
				"	forro.nombre_forro AS nombre,\n" + 
				"	'Materia Prima' AS clasficacion,\n" + 
				"	'Forro',\n" + 
				"	'0',\n" + 
				"	look.nombre_lookup AS medida,\n" + 
				"	forro.estatus,\n" + 
				"	'f' \n" + 
				"FROM\n" + 
				"	alt_disenio_forro AS forro,\n" + 
				"	alt_disenio_lookup AS look \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND look.id_lookup = forro.id_unidad_medida \n" + 
				//"	AND forro.estatus = 1 \n" + 
				"	AND forro.estatus_forro = 1 UNION\n" + 
				"SELECT\n" + 
				"	inventario.id_inventario,\n" + 
				"	inventario.id_text,\n" + 
				"	inventario.articulo AS nombre,\n" + 
				"	look_clas.nombre_lookup AS clasficacion,\n" + 
				"	look.nombre_lookup,\n" + 
				"	'0',\n" + 
				"	look2.nombre_lookup AS medida,\n" + 
				"	inventario.estatus,\n" + 
				"	'aa' \n" + 
				"FROM\n" + 
				"	alt_amp_inventario AS inventario,\n" + 
				"	alt_amp_lookup AS look,\n" + 
				"	alt_amp_lookup AS look_clas,\n" + 
				"	alt_disenio_lookup AS look2 \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND inventario.id_linea = look.id_lookup \n" + 
				"	AND inventario.id_unidad_medida = look2.id_lookup \n" + 
				"	AND look_clas.id_lookup = look.descripcion_lookup \n" + 
				"ORDER BY\n" + 
				"	clasficacion,\n" + 
				"	nombre ASC").getResultList();
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

package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpRequisicionAlmacenServiceImpl implements IAmpRequisicionAlmacenService {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Object[] infoUsuario(String user) {
		
		Query q = em.createNativeQuery("SELECT \r\n" + 
				"CONCAT(empleado.nombre_persona ,' ',empleado.apellido_paterno),\r\n" + 
				"nombre_departamento\r\n" + 
				"FROM\r\n" + 
				"alt_hr_usuario as usuario,\r\n" + 
				"alt_hr_empleado as empleado,\r\n" + 
				"alt_hr_departamento as depa,\r\n" + 
				"alt_hr_puesto as puesto\r\n" + 
				"WHERE \r\n" + 
				"2=2\r\n" + 
				"and usuario.id_empleado = empleado.id_empleado\r\n" + 
				"and empleado.id_puesto = puesto.id_puesto\r\n" + 
				"and puesto.id_departamento = depa.id_departamento\r\n" + 
				"and usuario.nombre_usuario = '"+user+"'" );
	
		Object[] valores = (Object[]) q.getSingleResult();
		return valores;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> AllMateriales() {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	material.id_material,\r\n" + 
				"	material.id_text,\r\n" + 
				"	material.nombre_material AS nombre,\r\n" + 
				"	look.nombre_lookup AS medida,\r\n" + 
				"	material.tamanio,\r\n" + 
				"	color.nombre_lookup,\r\n" + 
				"	'm' \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_material AS material,\r\n" + 
				"	alt_disenio_lookup AS look,\r\n" + 
				"	alt_disenio_lookup AS color \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND material.id_unidad_medida = look.id_lookup \r\n" + 
				"	AND material.id_color = color.id_lookup \r\n" + 
				"	AND material.estatus_material = 1 \r\n" + 
				"	AND material.estatus = 1 UNION\r\n" + 
				"SELECT\r\n" + 
				"	tela.id_tela,\r\n" + 
				"	tela.id_text,\r\n" + 
				"	tela.nombre_tela AS nombre,\r\n" + 
				"	look.nombre_lookup AS medida,\r\n" + 
				"	tela.ancho,\r\n" + 
				"	tela.color,\r\n" + 
				"	't' \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_tela AS tela,\r\n" + 
				"	alt_disenio_lookup AS look \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look.id_lookup = tela.id_unidad_medida \r\n" + 
				"	AND tela.estatus_tela = 1 \r\n" + 
				"	AND tela.estatus = 1 UNION\r\n" + 
				"SELECT\r\n" + 
				"	forro.id_forro,\r\n" + 
				"	forro.id_text,\r\n" + 
				"	forro.nombre_forro AS nombre,\r\n" + 
				"	look.nombre_lookup AS medida,\r\n" + 
				"	forro.ancho_forro,\r\n" + 
				"	forro.color,\r\n" + 
				"	'f' \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_forro AS forro,\r\n" + 
				"	alt_disenio_lookup AS look \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look.id_lookup = forro.id_unidad_medida \r\n" + 
				"	AND forro.estatus_forro = 1 \r\n" + 
				"	AND forro.estatus = 1 UNION\r\n" + 
				"SELECT\r\n" + 
				"	inventario.id_inventario,\r\n" + 
				"	inventario.id_text,\r\n" + 
				"	inventario.articulo AS nombre,\r\n" + 
				"	look2.nombre_lookup AS medida,\r\n" + 
				"	'No hay puto tamaño',\r\n" + 
				"	inventario.color,\r\n" + 
				"	'aa' \r\n" + 
				"FROM\r\n" + 
				"	alt_amp_inventario AS inventario,\r\n" + 
				"	alt_amp_lookup AS look,\r\n" + 
				"	alt_amp_lookup AS look_clas,\r\n" + 
				"	alt_disenio_lookup AS look2 \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND inventario.id_unidad_medida = look2.id_lookup \r\n" + 
				"	AND inventario.estatus = 1 \r\n" + 
				"ORDER BY\r\n" + 
				"	id_text,\r\n" + 
				"	nombre").getResultList();
		
		return re;
	}
	
	

}

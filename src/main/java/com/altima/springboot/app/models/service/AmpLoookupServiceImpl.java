package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.AmpLookup;
import com.altima.springboot.app.repository.AmpLookupRepository;

@Service
public class AmpLoookupServiceImpl implements IAmpLoookupService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AmpLookupRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<AmpLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<AmpLookup>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(AmpLookup lookup) {
		// TODO Auto-generated method stub
		repository.save(lookup);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public AmpLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("nombreLookup ASC")
	public List<AmpLookup> findAllLookup(String Tipo) {
		if (Tipo.equals("Clasificacion")) {
			return em.createQuery("from AmpLookup where tipo_lookup='" + Tipo + "' ORDER BY nombre_lookup ASC").getResultList();
		}
		else {
			return em.createQuery("from AmpLookup where tipo_lookup='" + Tipo + "' ORDER BY nombre_lookup ASC ").getResultList();
		}
	}

	@Override
	@Transactional
	public AmpLookup findLastLookupByType(String Tipo) {
		return (AmpLookup) em.createQuery("from AmpLookup where tipo_lookup='" + Tipo + "' ORDER BY idLookup DESC")
				.setMaxResults(1).getSingleResult();
	}

	@Override
	@Transactional
	public boolean findDuplicate(String Lookup, String Tipo) {
		boolean duplicate;
		@SuppressWarnings("unchecked")

		List<AmpLookup> result = em
				.createQuery("from AmpLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'")
				.getResultList();
		if (result.isEmpty()) {
			duplicate = false;

		} else {
			duplicate = true;
		}
		return duplicate;
	}

	@Override
	@Transactional
	public boolean findDuplicate(String Lookup, String Tipo, String atributo) {

		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<AmpLookup> result = em.createQuery("from AmpLookup where nombreLookup='" + Lookup + "' and tipoLookup='"
				+ Tipo + "' and atributo1='" + atributo + "'").getResultList();
		if (result.isEmpty()) {
			duplicate = false;
		} else {
			duplicate = true;
		}
		return duplicate;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<Object[]> listarLinea(Long id) {
		return em.createNativeQuery("" + "SELECT\n" + "	linea.id_lookup,\n" + "	linea.nombre_lookup \n" + "FROM\n"
				+ "	alt_amp_lookup AS linea,\n" + "	alt_amp_lookup AS cate \n" + "WHERE\n" + "	1 = 1 \n"
				+ "	AND linea.tipo_lookup = 'Linea' \n" + "	AND linea.descripcion_lookup = cate.id_lookup \n"
				+ "	AND linea.estatus = 1 \n" + "	AND cate.id_lookup = " + id).getResultList();
	}

	@Override
	@Transactional
	public String nombreCategoria(Long id) {

		return em
				.createNativeQuery("" + "SELECT DISTINCT\n" + "	nombre_lookup \n" + "FROM\n"
						+ "	alt_amp_lookup as look\n" + "WHERE\n" + "	look.id_lookup=" + id)
				.getResultList().toString();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AmpLookup> findAllMovements() {
		// TODO Auto-generated method stub
		return em.createQuery("From AmpLookup where tipoLookup IN('Entrada','Salida')").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmpLookup> findMovementsDuplicate(String movimiento, String tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("From AmpLookup where nombreLookup='" + movimiento + "' and tipoLookup='" + tipo + "' ")
				.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<Object []> findAllLinea() {
		return em.createNativeQuery(""
				+ "SELECT\n" + 
				"	amp.id_lookup,\n" + 
				"	amp.id_text,\n" + 
				"	amp.nombre_lookup,\n" + 
				"	clasificacion.nombre_lookup AS descripcion_lookup,\n" + 
				"	amp.estatus,\n" + 
				"	amp.creado_por,\n" + 
				"	DATE_FORMAT( amp.fecha_creacion, '%Y-%m-%d %T' ),\n" + 
				"IF\n" + 
				"	( amp.actualizado_por IS NULL, '', amp.actualizado_por ),\n" + 
				"IF\n" + 
				"	(\n" + 
				"		amp.ultima_fecha_modificacion IS NULL,\n" + 
				"		' ',\n" + 
				"	DATE_FORMAT( amp.ultima_fecha_modificacion, '%Y-%m-%d %T' )),\n" + 
				"	clasificacion.id_lookup as id_clas \n" + 
				"FROM\n" + 
				"	alt_amp_lookup AS amp,\n" + 
				"	alt_amp_lookup AS clasificacion \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND clasificacion.id_lookup = amp.descripcion_lookup \n" + 
				"	AND amp.tipo_lookup = 'Linea' \n" + 
				"ORDER BY\n" + 
				"	amp.id_text,\n" + 
				"	amp.nombre_lookup ASC").getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<Object []> findAllAlmacen() {
		return em.createNativeQuery("SELECT\n" + 
				"	almacen.id_almacen_fisico,\n" + 
				"	almacen.nombre_almacen \n" + 
				"FROM\n" + 
				"	alt_amp_almacen_fisico AS almacen \n" + 
				"WHERE\n" + 
				"	almacen.estatus =1").getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<Object []> findAllPasillos() {
		return em.createNativeQuery("SELECT\n" + 
				"	amp.id_lookup,\n" + 
				"	amp.id_text,\n" + 
				"	amp.nombre_lookup,\n" + 
				"	almacen.nombre_almacen AS descripcion_lookup,\n" + 
				"	amp.estatus,\n" + 
				"	amp.creado_por,\n" + 
				"	DATE_FORMAT( amp.fecha_creacion, '%Y-%m-%d %T' ),\n" + 
				"IF\n" + 
				"	( amp.actualizado_por IS NULL, '', amp.actualizado_por ),\n" + 
				"IF\n" + 
				"	(\n" + 
				"		amp.ultima_fecha_modificacion IS NULL,\n" + 
				"		' ',\n" + 
				"	DATE_FORMAT( amp.ultima_fecha_modificacion, '%Y-%m-%d %T' )),\n" + 
				"	almacen.id_almacen_fisico \n" + 
				"FROM\n" + 
				"	alt_amp_lookup AS amp,\n" + 
				"	alt_amp_almacen_fisico AS almacen \n" + 
				"WHERE\n" + 
				"	amp.atributo_1 = almacen.id_almacen_fisico").getResultList();
		
	}
}

package com.altima.springboot.app.models.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.MaquilaLookup;
import com.altima.springboot.app.repository.MaquilaLookupRepository;

@Service
public class MaquilaLookupServiceImpl implements IMaquilaLookupService {

    @PersistenceContext
	private EntityManager em;

	@Autowired
    private MaquilaLookupRepository repository;
    
    
	@Override
	@Transactional(readOnly = true)
	public List<MaquilaLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<MaquilaLookup>) repository.findAll();
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("nombreLookup ASC")
	public List<MaquilaLookup> findAll(String tipo, String estatus) {
		return em.createQuery("from MaquilaLookup where tipo_lookup='" + tipo + "' AND estatus="+estatus+" ORDER BY nombre_lookup ASC ").getResultList();
	}

	@Override
	@Transactional
	public void save(MaquilaLookup lookup) {
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
	public MaquilaLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("nombreLookup ASC")
	public List<MaquilaLookup> findAllLookup(String Tipo) {
		if (Tipo.equals("Clasificacion")) {
			return em.createQuery("from MaquilaLookup where tipo_lookup='" + Tipo + "' ORDER BY nombre_lookup ASC").getResultList();
		}
		else {
			return em.createQuery("from MaquilaLookup where tipo_lookup='" + Tipo + "' ORDER BY nombre_lookup ASC ").getResultList();
		}
	}

	@Override
	@Transactional
	public MaquilaLookup findLastLookupByType(String Tipo) {
		return (MaquilaLookup) em.createQuery("from MaquilaLookup where tipo_lookup='" + Tipo + "' ORDER BY idLookup DESC")
				.setMaxResults(1).getSingleResult();
	}

	@Override
	@Transactional
	public boolean findDuplicate(String Lookup, String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 ) {
		String query ="";
		switch(Tipo){
			case "Familia": 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Activo Fijo" : 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "AFI" : 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup ='"+descripcion+"'";
			break;
			case "Componente" : 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Color" : 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup='"+descripcion+"' and atributo1='"+atributo1+"'  and atributo2='"+atributo2+"'";
			break;
			//MAF
			case "MAF" : 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup='"+descripcion+"'";
			break;

			case "MIN" : 
				query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup='"+descripcion+"'";
			break;
			case "Operacion" : 
			query="from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup='"+descripcion+"' and atributo1='"+atributo1+"'  and atributo2='"+atributo2+"' and atributo3='"+atributo3+"' ";
			break;

		}

		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<MaquilaLookup> result = em
				.createQuery(query)
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
		List<MaquilaLookup> result = em.createQuery("from MaquilaLookup where nombreLookup='" + Lookup + "' and tipoLookup='"
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
	public List<Object[]> listarActivos() {
		List<Object[]> re = null;
			re = em.createNativeQuery(""+
				"SELECT\r\n"+
					"look1.id_lookup,\r\n"+
					"look1.id_text,\r\n"+
					"look1.nombre_lookup,\r\n"+
					"look2.nombre_lookup as activo,\r\n"+
					"look1.creado_por,\r\n"+
					"DATE_FORMAT(	look1.fecha_creacion,'%Y-%m-%d %T'),\r\n"+
					"look1.actualizado_por,\r\n"+
					"DATE_FORMAT(	look1.ultima_fecha_modificacion,'%Y-%m-%d %T'),\r\n"+
					"look1.estatus\r\n"+
				"FROM\r\n"+
					"alt_maquila_lookup AS look1\r\n"+
					"INNER JOIN alt_maquila_lookup look2 ON look1.descripcion_lookup = look2.id_lookup").getResultList();

		return re;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listarColor() {
		List<Object[]> re = null;
			re = em.createNativeQuery(""+
				"SELECT\r\n"+
					"look1.id_lookup,\r\n"+
					"look1.id_text,\r\n"+
					"look1.nombre_lookup,\r\n"+
					"look1.descripcion_lookup,\r\n"+
					"pro.nombre_proveedor,\r\n"+
					"look1.atributo_2,\r\n"+
					"look1.estatus,\r\n"+
					"look1.creado_por,\r\n"+
					"DATE_FORMAT(	look1.fecha_creacion,'%Y-%m-%d %T'),\r\n"+
					"look1.actualizado_por,\r\n"+
					"DATE_FORMAT(	look1.ultima_fecha_modificacion,'%Y-%m-%d %T')\r\n"+
				"FROM\r\n"+
					"alt_maquila_lookup AS look1\r\n"+
					"INNER JOIN alt_compras_proveedor pro ON look1.atributo_1 = pro.id_proveedor\r\n"+
				"WHERE\r\n"+
					"1 = 1\r\n"+
					"AND look1.tipo_lookup = 'Color'").getResultList();

		return re;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listarFamiliabyMaquinaria() {
		List<Object[]> re = null;
			re = em.createNativeQuery(""+
				"SELECT\r\n"+
					"look1.id_lookup,\r\n"+
					"look1.nombre_lookup\r\n"+
				"FROM\r\n"+
					"alt_maquila_lookup AS look1\r\n"+
					"INNER JOIN alt_maquila_lookup look2 ON look1.descripcion_lookup = look2.id_lookup\r\n"+ 
				"WHERE\r\n"+
					"look2.nombre_lookup = 'Maquinaria' \r\n"+
					"AND look1.estatus = 1 \r\n"+
				"ORDER BY\r\n"+
					"look1.nombre_lookup").getResultList();

		return re;

	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> Operaciones() {
		List<Object[]> re = null;
			re = em.createNativeQuery(""
					+ "SELECT\r\n" + 
					"	look1.id_lookup as id,\r\n" + 
					"	look1.id_text as idText,\r\n" + 
					"	familia.nombre_lookup as famila,\r\n" + 
					"	look1.nombre_lookup as operacion,\r\n" + 
					"	maquina.nombre_lookup as maquina,\r\n" + 
					"	look1.atributo_2 as sam,\r\n" + 
					"	ROUND((60/ look1.atributo_2)) as hrs,\r\n" + 
					"	ROUND(((60/ look1.atributo_2)* look1.atributo_3)) as turno,\r\n" + 
					"	look1.estatus,\r\n" + 
					"	look1.creado_por,\r\n" + 
					"	DATE_FORMAT(	look1.fecha_creacion,'%Y-%m-%d %T'),\r\n" + 
					"	look1.actualizado_por,\r\n" + 
					"	DATE_FORMAT(	look1.ultima_fecha_modificacion,'%Y-%m-%d %T')\r\n" + 
					"	\r\n" + 
					"FROM\r\n" + 
					"	alt_maquila_lookup AS look1\r\n" + 
					"	INNER JOIN alt_maquila_lookup familia on look1.descripcion_lookup=familia.id_lookup\r\n" + 
					"	INNER JOIN alt_maquila_lookup maquina on look1.atributo_1 = maquina.id_lookup\r\n" + 
					"	\r\n" + 
					"	WHERE\r\n" + 
					"	1=1\r\n" + 
					"	and look1.tipo_lookup='Operacion'").getResultList();

		return re;

	}
	

    
}

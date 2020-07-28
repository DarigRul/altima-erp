package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ProduccionConsumoTalla;
import com.altima.springboot.app.repository.ProduccionConsumoTallaRepository;
@Service
public class ProduccionConsumoTallaServiceImpl implements IProduccionConsumoTallaService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ProduccionConsumoTallaRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public List<ProduccionConsumoTalla> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionConsumoTalla>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(ProduccionConsumoTalla ProduccionConsumoTalla) {
		// TODO Auto-generated method stub
		repository.save(ProduccionConsumoTalla);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public ProduccionConsumoTalla findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> tallas(Long id) {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	talla.id_lookup,\r\n" + 
				"	talla.nombre_lookup \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_prenda AS prenda,\r\n" + 
				"	alt_produccion_lookup AS talla,\r\n" + 
				"	alt_disenio_lookup AS lookup \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND prenda.id_familia_prenda = lookup.id_lookup \r\n" + 
				"	AND lookup.atributo_1 = talla.descripcion_lookup \r\n" + 
				"	AND talla.tipo_lookup = 'Talla' \r\n" + 
				"	AND prenda.id_genero = talla.atributo_1 \r\n" + 
				"	AND prenda.id_prenda = "+id+" \r\n" + 
				"	AND NOT EXISTS ( SELECT * FROM alt_produccion_consumo_talla AS con WHERE con.id_talla = talla.id_lookup AND con.id_prenda = "+id+" )").getResultList();
		return re;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> largo() {
		List<String> re = em.createNativeQuery("SELECT\r\n" + 
				"	look.nombre_lookup \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_lookup AS look \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look.estatus = 1 \r\n" + 
				"	AND look.tipo_lookup = 'Largo'").getResultList();
		return re;
	}
	
	@Override
	public String genpivot(List<String> list) {
		String res = null;
		// 	MAX( CASE WHEN look.nombre_lookup = 'XL' THEN consumo.consumo END ) XL,
		try {
			List<String> query = new ArrayList<>();
			for (String string : list) {
				query.add("MAX(case when look.nombre_lookup='" + string + "' then consumo.consumo else NULL end) as '" + string+ "'");

			}
			String delim = ",";

			StringBuilder sb = new StringBuilder();

			int i = 0;
			while (i < query.size() - 1) {
				sb.append(query.get(i));
				sb.append(delim);
				i++;
			}
			sb.append(query.get(i));

			res = sb.toString();
			
			return res;
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> Consumo_Talla(Long id , String Cabezal){
		List<Object[]> queryresult;
		 

			queryresult = em.createNativeQuery("SELECT\r\n" + 
					"	look2.nombre_lookup,\r\n" + 
					Cabezal +
					"	\r\n" + 
					"FROM\r\n" + 
					"	alt_produccion_consumo_talla AS consumo,\r\n" + 
					"	alt_produccion_lookup AS look,\r\n" + 
					"	alt_produccion_lookup AS look2 \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND look2.id_lookup = consumo.id_talla \r\n" + 
					"	AND consumo.id_tipo_largo = look.id_lookup \r\n" + 
					"	AND consumo.id_prenda = "+id+" \r\n" + 
					"GROUP BY\r\n" + 
					"	look2.nombre_lookup").getResultList();

		
		return queryresult;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> Consumo_Talla_id(Long id) {
		List<String> re = em.createNativeQuery("SELECT\r\n" + 
				"	look2.id_lookup\r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_consumo_talla AS consumo,\r\n" + 
				"	alt_produccion_lookup AS look,\r\n" + 
				"	alt_produccion_lookup AS look2 \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look2.id_lookup = consumo.id_talla \r\n" + 
				"	AND consumo.id_tipo_largo = look.id_lookup \r\n" + 
				"	AND consumo.id_prenda = "+id+" \r\n" + 
				"GROUP BY\r\n" + 
				"	look2.nombre_lookup").getResultList();
		return re;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ConsumoTalla_Tallas(Long idTalla, Long idPrenda) {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	id,\r\n" + 
				"	nombre,\r\n" + 
				"	consumo \r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"		(\r\n" + 
				"		SELECT\r\n" + 
				"			consumo.id_tipo_largo AS id,\r\n" + 
				"			look.nombre_lookup AS nombre,\r\n" + 
				"			consumo.consumo AS consumo \r\n" + 
				"		FROM\r\n" + 
				"			alt_produccion_consumo_talla AS consumo,\r\n" + 
				"			alt_produccion_lookup AS look \r\n" + 
				"		WHERE\r\n" + 
				"			1 = 1 \r\n" + 
				"			AND consumo.id_tipo_largo = look.id_lookup \r\n" + 
				"			AND consumo.id_prenda = "+idPrenda+" \r\n" + 
				"			AND consumo.id_talla = "+idTalla+" \r\n" + 
				"		) UNION\r\n" + 
				"		(\r\n" + 
				"		SELECT\r\n" + 
				"			look.id_lookup AS id,\r\n" + 
				"			look.nombre_lookup AS nombre,\r\n" + 
				"			'0' AS consumo \r\n" + 
				"		FROM\r\n" + 
				"			alt_produccion_lookup AS look \r\n" + 
				"		WHERE\r\n" + 
				"			1 = 1 \r\n" + 
				"			AND look.tipo_lookup = 'Largo'\r\n" + 
				"			AND look.estatus=1\r\n" + 
				"		) \r\n" + 
				"	) t \r\n" + 
				"GROUP BY\r\n" + 
				"	nombre \r\n" + 
				"ORDER BY\r\n" + 
				"	id").getResultList();
		return re;
	}
	
	@Override
	@Transactional
	public ProduccionConsumoTalla buscar_consumo(Long idTalla , Long idPrenda , Long idLargo) {
		// TODO Auto-generated method stub
		try {
			return  (ProduccionConsumoTalla)em.createQuery("from ProduccionConsumoTalla  where id_talla ="+idTalla +" AND  id_prenda="+idPrenda +" AND id_tipo_largo="+idLargo ).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
	}
}

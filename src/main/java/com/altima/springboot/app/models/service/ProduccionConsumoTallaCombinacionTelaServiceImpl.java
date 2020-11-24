package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ProduccionConsumoTallaCombinacionTela;
import com.altima.springboot.app.repository.ProduccionConsumoTallaCombinacionTelaRepository;

@Service
public class ProduccionConsumoTallaCombinacionTelaServiceImpl implements IProduccionConsumoTallaCombinacionTelaService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ProduccionConsumoTallaCombinacionTelaRepository repository;
	
	
	@Override
	public List<ProduccionConsumoTallaCombinacionTela> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void save(ProduccionConsumoTallaCombinacionTela obj) {
		// TODO Auto-generated method stub
		repository.save(obj);
	}


	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ProduccionConsumoTallaCombinacionTela findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public ProduccionConsumoTallaCombinacionTela findOneSpecial(Long id) {
		// TODO Auto-generated method stub
		return (ProduccionConsumoTallaCombinacionTela) em.createQuery("From ProduccionConsumoTallaCombinacionTela where IdTallaEspecial=1 and idPrenda="+id+"  ").getSingleResult();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> tallas(Long id) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	talla.id_lookup,\r\n" + 
				"	talla.nombre_lookup \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_prenda AS prenda,\r\n" + 
				"	alt_produccion_lookup AS talla\r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND talla.tipo_lookup = 'Talla' \r\n" + 
				"	AND prenda.id_genero = talla.atributo_1 \r\n" + 
				"	AND prenda.id_prenda = "+id+" \r\n" +
				"	AND NOT EXISTS ( SELECT * FROM alt_produccion_consumo_talla_combinacion_tela AS con WHERE con.id_talla = talla.id_lookup AND con.id_prenda = "+id+" )").getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> largos() {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	look.id_lookup,\r\n" + 
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
				query.add("MAX(case when look.nombre_lookup='" + string + "' then consumo.consumo_ancho else NULL end) as '" + string+ "',"+
						 "MAX(case when look.nombre_lookup='" + string + "' then consumo.consumo_largo else NULL end) as '" + string+ "2'"
						);

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
	public List<Object[]> Consumo_Talla(Long id , String Cabezal, Long idTela){
		List<Object[]> queryresult;
		 
			
			queryresult = em.createNativeQuery("SELECT\r\n" + 
					"	look2.nombre_lookup,\r\n" + 
					Cabezal +
					"	\r\n" + 
					"FROM\r\n" + 
					"	alt_produccion_consumo_talla_combinacion_tela AS consumo,\r\n" + 
					"	alt_produccion_lookup AS look,\r\n" + 
					"	alt_produccion_lookup AS look2 \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND look2.id_lookup = consumo.id_talla \r\n" + 
					"	AND consumo.id_tipo_largo = look.id_lookup \r\n" + 
					"	AND consumo.id_prenda = "+id+" \r\n" + 
					
					"	AND consumo.id_material = "+idTela+" \r\n" + 
				
					"GROUP BY\r\n" + 
					"	look2.nombre_lookup").getResultList();

		
		return queryresult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> Consumo_Talla_id(Long id, Long idTela) {
		List<String> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	look2.id_lookup \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_consumo_talla_combinacion_tela AS consumo,\r\n" + 
				"	alt_produccion_lookup AS look,\r\n" + 
				"	alt_produccion_lookup AS look2 \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look2.id_lookup = consumo.id_talla \r\n" + 
				"	AND consumo.id_tipo_largo = look.id_lookup \r\n" + 
				"	AND consumo.id_prenda = "+id+" \r\n" + 
				"	AND consumo.id_material = "+idTela+" \r\n" + 
				"GROUP BY\r\n" + 
				"	look2.nombre_lookup").getResultList();
		return re;
	}
	@Override
	@Transactional
	public ProduccionConsumoTallaCombinacionTela buscar_consumo(Long idTalla , Long idPrenda , Long idLargo, Long idTela) {
		// TODO Auto-generated method stub
		System.out.println("from ProduccionConsumoTallaCombinacionTela  where id_talla ="+idTalla +" AND  id_prenda="+idPrenda +" AND id_tipo_largo="+idLargo +" AND id_material="+idTela);
		try {
			return  (ProduccionConsumoTallaCombinacionTela)em.createQuery("from ProduccionConsumoTallaCombinacionTela  where id_talla ="+idTalla +" AND  id_prenda="+idPrenda +" AND id_tipo_largo="+idLargo +" AND id_material="+idTela).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
	}
}

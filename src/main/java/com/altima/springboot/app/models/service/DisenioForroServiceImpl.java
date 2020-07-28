package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.DisenioForro;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.DisenioForroRepository;

@Service
public class DisenioForroServiceImpl implements IDisenioForroService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private DisenioForroRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioForro> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioForro>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioForro disenioforro) {
		// TODO Auto-generated method stub
		repository.save(disenioforro);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioForro findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ForrosSelect(Long id) {
		// TODO Auto-generated method stub
		/*List<Object[]> re = em
				.createNativeQuery("select f.id_forro ,f.nombre_forro \r\n" + "from 	alt_disenio_forro as f \r\n"
						+ "where not exists (select alt_disenio_tela_forro.id_forro, f.nombre_forro from  \r\n"
						+ "					alt_disenio_tela_forro \r\n" + "					where 1=1\r\n"
						+ "					and alt_disenio_tela_forro.id_forro= f.id_forro \r\n"
						+ "					and alt_disenio_tela_forro.id_tela=" + id + ")")
				.getResultList();*/
		
		
		List<Object[]> re = em
				.createNativeQuery("select f.id_forro ,CONCAT(f.id_text,' ',f.nombre_forro,' ', f.color) As inf\r\n" + 
						"		from 	alt_disenio_forro as f \r\n" + 
						"				where not exists (select tf.id_forro, f.nombre_forro from alt_disenio_tela_forro as tf			\r\n" + 
						"							where 1=1\r\n" + 
						"							and tf.id_forro= f.id_forro\r\n" + 
						"							and tf.id_tela="+id+")\r\n" + 
						"							and f.estatus=1\r\n" + 
						"							and f.estatus_forro=1\r\n" + 
						"							ORDER BY inf")
				.getResultList();
		
		
		
		
		
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> ForroUnidadMedida(Long id) {
		
		List<Object[]> re = em
				.createNativeQuery("SELECT adl.id_lookup, adl.nombre_lookup, adl.tipo_lookup\r\n" + 
						"		FROM alt_disenio_lookup adl INNER JOIN alt_disenio_forro adf \r\n" + 
						"				WHERE adf.id_unidad_medida = adl.id_lookup and adl.tipo_lookup = 'Unidad Medida'\r\n" +
						"				and adl.estatus=1 order by adl.nombre_lookup and adf.id_forro="+id).getResultList();
		
		return re;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioForro> forrosAutorizados() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioForro where estatus=1 and estatus_forro=1").getResultList();
	}
	
	@Transactional
	@Override
	public String EstatusCalidadForro(Long id){
		
		return em.createNativeQuery("SELECT \r\n" + 
				 "				CASE WHEN adc.estatus IS NULL or adc.estatus = '' \r\n" +
				 "				THEN COUNT(adc.estatus) \r\n" +
				 "				ELSE adc.estatus \r\n" +
				 "				END AS estatus \r\n" +
				 "				FROM alt_disenio_calidad adc \r\n" +
				 "				INNER JOIN alt_disenio_forro adf ON adc.id_material = adf.id_forro \r\n" +
				 "				WHERE 1=1 AND adc.tipo_material=3 AND adf.id_forro="+id).getSingleResult().toString();
	}
	@Override
	@Transactional
	public String buscar_forro(String nombre) {
		
		
			String re = em.createNativeQuery("SELECT\r\n" + 
					"	COUNT( forro.nombre_forro ) \r\n" + 
					"FROM\r\n" + 
					"	alt_disenio_forro AS forro \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND forro.nombre_forro = '"+nombre+"'").getSingleResult().toString();
				
			return re;
		
		
		
	
	}
	
}

package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.repository.ProduccionLookupRepository;
@Service
public class ProduccionLookupServiceImpl implements IProduccionLookupService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ProduccionLookupRepository repository;

	@Override
	@Transactional(readOnly=true)
	public List<ProduccionLookup> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionLookup>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(ProduccionLookup ProduccionLookup) {
		// TODO Auto-generated method stub
		repository.save(ProduccionLookup);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public ProduccionLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<ProduccionLookup> findAllLookup(String Tipo) {
		return em.createQuery("from ProduccionLookup where tipo_lookup='"+Tipo+"'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@OrderBy("idLookup ASC")
	public List<ProduccionLookup> findAllLookup(String Tipo, String estatus) {
		return em.createQuery("from ProduccionLookup where tipo_lookup='"+Tipo+"' and estatus="+estatus).getResultList();
	}
	
	@Override
	@Transactional
	public ProduccionLookup findLastLookupByType(String Tipo){
		return (ProduccionLookup) em.createQuery("from ProduccionLookup where tipo_lookup='"+Tipo+"' ORDER BY idLookup DESC").setMaxResults(1).getSingleResult();
	}
	 
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<ProduccionLookup> result = em.createQuery("from ProduccionLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"'").getResultList();
		
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo,String descripcion){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<ProduccionLookup> result = em.createQuery("from ProduccionLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"' and descripcion_lookup= '"+descripcion +"'").getResultList();
		
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}
	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup,String Tipo,String atributo1, String atributo2){
		boolean duplicate;
		@SuppressWarnings("unchecked")
		
		List<ProduccionLookup> result = em.createQuery("from ProduccionLookup where nombreLookup='"+Lookup+"' and tipoLookup='"+Tipo+"' and atributo1='"+atributo1+"' and atributo2='"+atributo2+"'" ).getResultList();
		
		if(result.isEmpty()) {
			duplicate=false;
		}
		else {
			duplicate=true;
		}
		 return duplicate;
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProduccionLookup> findAllByType(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ProduccionLookup where Estatus=1 and tipoLookup='"+Tipo+"'").getResultList();
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProduccionLookup> findAllByType(String Posicion,String Genero ,String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ProduccionLookup where Estatus=1 and tipoLookup='Talla' and atributo1='"+Genero+"' ").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> encargadoUbicaciones() {
		List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n"+
				"empleado.id_empleado,\r\n"+
				"CONCAT( empleado.nombre_persona, ' ', empleado.apellido_paterno,' ',empleado.apellido_materno )\r\n"+
			"FROM\r\n"+
				"alt_hr_empleado empleado\r\n"+
				"INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\r\n"+
				"INNER JOIN alt_hr_departamento depa ON puesto.id_departamento = depa.id_departamento\r\n"+
				"INNER JOIN alt_hr_lookup look ON look.id_lookup = depa.id_area\r\n"+
			"WHERE\r\n"+
				"1 = 1 \r\n"+
				"AND look.nombre_lookup = 'PRODUCCION' \r\n"+
				"AND depa.nombre_departamento = 'MAQUILA' \r\n"+
				"AND puesto.nombre_puesto = 'CHOFER' \r\n"+
				"AND empleado.estatus=1").getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listarUbicaciones() {
		List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n"+
				"look.id_lookup,\r\n"+
				"look.id_text,\r\n"+	
				"look.nombre_lookup,\r\n"+
				"'encargado' encargado,\r\n"+
				"look.estatus,\r\n"+
				"look.creado_por,\r\n"+
				"DATE_FORMAT(	look.fecha_creacion,'%Y-%m-%d %T'),\r\n"+
				"look.actualizado_por,\r\n"+
				"DATE_FORMAT(	look.ultima_fecha_modificacion,'%Y-%m-%d %T'),'0' id_empleado\r\n"+
			"FROM\r\n"+
				"alt_produccion_lookup AS look\r\n"+
			"WHERE\r\n"+
				"1 = 1 \r\n"+
				"AND look.tipo_lookup = 'Ubicación'").getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ProduccionLookup> findAllByMaquilero(Long idMaquilero) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT aplproceso.*FROM alt_produccion_lookup aplproceso INNER JOIN alt_produccion_maquilador_proceso apmp ON apmp.id_proceso=aplproceso.id_lookup WHERE apmp.id_maquilador=:idMaquilero and aplproceso.estatus=1",ProduccionLookup.class).setParameter("idMaquilero", idMaquilero).getResultList();
	}


}

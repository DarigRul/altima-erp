package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altima.springboot.app.models.entity.SoporteTecnicoLookup;
import com.altima.springboot.app.repository.SoporteTecnicoLookupRepository;

@Service
public class SopoteTecnicoLookupServiceImpl implements ISoporteTecnicoLookupService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired 
	private SoporteTecnicoLookupRepository repository;
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<SoporteTecnicoLookup> findAllByType(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from SoporteTecnicoLookup where estatus=1 and tipoLookup='"+Tipo+"'").getResultList();
	}

	@Override
	public void save(SoporteTecnicoLookup SoporteTecnicoLookup) {
		repository.save(SoporteTecnicoLookup);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public SoporteTecnicoLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup, String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 ) {
		String query ="";
		switch(Tipo){
			case "Equipo": 
			query="from SoporteTecnicoLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup ='"+descripcion+"'";
			break;
			case "Categoria": 
			query="from SoporteTecnicoLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Arreglo":
			query="from SoporteTecnicoLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Especificación":
			query="from SoporteTecnicoLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Actividad":
			query="from SoporteTecnicoLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Material":
			query="from SoporteTecnicoLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup ='"+descripcion+"'";
			break;
		
		}
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<SoporteTecnicoLookup> result = em.createQuery(query).getResultList();
		if (result.isEmpty()) {
			duplicate = false;

		} else {
			duplicate = true;
		}
		return duplicate;
	}

	@Override
	@Transactional
	public SoporteTecnicoLookup findLastLookupByType(String Tipo) {
		return (SoporteTecnicoLookup) em.createQuery("from SoporteTecnicoLookup where tipo_lookup='" + Tipo + "' ORDER BY idLookup DESC")
				.setMaxResults(1).getSingleResult();
	}

	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<SoporteTecnicoLookup> findAllLookup(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from SoporteTecnicoLookup where tipoLookup='"+Tipo+"'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllPrendas() {
		List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n" +
				"look.id_lookup,\r\n" +
				"look.nombre_lookup\r\n" +
			"FROM\r\n" +
				"alt_disenio_lookup AS look\r\n" +
			"WHERE\r\n" +
				"1 = 1\r\n" +
				"AND look.estatus=1\r\n" +
				"AND look.tipo_lookup = 'Familia Prenda'\r\n" +
			"ORDER BY\r\n" +
				"look.nombre_lookup ASC").getResultList();

		return re;
	}


	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<SoporteTecnicoLookup> listaProcesos() {
		return em.createQuery("from SoporteTecnicoLookup where estatus=1 and tipoLookup='Proceso' and actualizadoPor is not null").getResultList();
	}


	
}

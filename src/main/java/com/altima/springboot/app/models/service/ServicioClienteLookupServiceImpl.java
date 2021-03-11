package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ServicioClienteArregloPrenda;
import com.altima.springboot.app.models.entity.ServicioClienteLookup;
import com.altima.springboot.app.repository.ServicioClienteArregloPrendaRepository;
import com.altima.springboot.app.repository.ServicioClienteLookupRepository;

@Service
public class ServicioClienteLookupServiceImpl implements IServicioClienteLookupService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired 
	private ServicioClienteLookupRepository repository;

	@Autowired
	private ServicioClienteArregloPrendaRepository ArregloPrendas;
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ServicioClienteLookup> findAllByType(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ServicioClienteLookup where estatus=1 and tipoLookup='"+Tipo+"'").getResultList();
	}

	@Override
	public void save(ServicioClienteLookup servicioclientelookup) {
		repository.save(servicioclientelookup);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public ServicioClienteLookup findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	
	@Override
	@Transactional
	public boolean findDuplicate(String Lookup, String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 ) {
		String query ="";
		switch(Tipo){
			case "Tipo ingreso": 
			query="from ServicioClienteLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup ='"+descripcion+"'";
			break;
			case "Complejidad": 
			query="from ServicioClienteLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup ='"+descripcion+"'";
			break;
			case "Arreglo":
			query="from ServicioClienteLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Especificación":
			query="from ServicioClienteLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Actividad":
			query="from ServicioClienteLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "'";
			break;
			case "Material":
			query="from ServicioClienteLookup where nombreLookup='" + Lookup + "' and tipoLookup='" + Tipo + "' and descripcionLookup ='"+descripcion+"'";
			break;
		
		}
		boolean duplicate;
		@SuppressWarnings("unchecked")
		List<ServicioClienteLookup> result = em.createQuery(query).getResultList();
		if (result.isEmpty()) {
			duplicate = false;

		} else {
			duplicate = true;
		}
		return duplicate;
	}

	@Override
	@Transactional
	public ServicioClienteLookup findLastLookupByType(String Tipo) {
		return (ServicioClienteLookup) em.createQuery("from ServicioClienteLookup where tipo_lookup='" + Tipo + "' ORDER BY idLookup DESC")
				.setMaxResults(1).getSingleResult();
	}

	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ServicioClienteLookup> findAllLookup(String Tipo) {
		// TODO Auto-generated method stub
		return em.createQuery("from ServicioClienteLookup where tipoLookup='"+Tipo+"'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllPrendas() {
		List<Object[]> re = null;
		re = em.createNativeQuery("SELECT id_prenda, descripcion_prenda FROM alt_disenio_prenda WHERE estatus = 1  ORDER BY detalle_prenda desc").getResultList();

		return re;
	}

	@Override
	public void saveArregloPrenda(ServicioClienteArregloPrenda obj) {
		ArregloPrendas.save(obj);
		
	}

	@Override
	public void deleteArregloPrenda(Long id) {
		ArregloPrendas.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> verPrendaArreglo(Long id) {
		List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n" +
				"ARPE.id_arreglo_prendas,\r\n" +
				"prenda.descripcion_prenda,\r\n" +
				"look.nombre_lookup,\r\n" +
				"ARPE.id_prenda,\r\n" +
				"ARPE.id_complejidad\r\n" +
			"FROM\r\n" +
				"alt_servicio_cliente_arreglo_prenda AS ARPE\r\n" +
				"INNER JOIN alt_disenio_prenda prenda ON ARPE.id_prenda = prenda.id_prenda\r\n" +
				"INNER JOIN alt_servicio_cliente_lookup look ON ARPE.id_complejidad = look.id_lookup\r\n" +
			"WHERE\r\n" +
				"1 = 1\r\n" +
				"AND ARPE.id_arreglo = "+id).getResultList();

		return re;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean findOnePrendaArreglo(Long idArreglo, String idPrenda, String idComplejidad) {
		String re = em.createNativeQuery(""+
			"SELECT\r\n" +
				"COUNT( ARRPE.id_arreglo_prendas )\r\n" +
			"FROM\r\n" +
				"alt_servicio_cliente_arreglo_prenda AS ARRPE\r\n" +
			"WHERE\r\n" +
				"1 = 1\r\n" +
				"AND ARRPE.id_arreglo = "+idArreglo+"\r\n" +
				"AND ARRPE.id_complejidad = "+idComplejidad+"\r\n" +
				"AND ARRPE.id_prenda = "+idPrenda).getSingleResult().toString();
        if ( re.equals("0")){
            return false;
        }else{
            return true;
        }
	}
	
    @Transactional(readOnly = true)
	@Override
    public boolean validarNombreArregloEditar(Long idLookup, String nombre) {
        String re = em.createNativeQuery(""+
            "SELECT \r\n" + 
            "COUNT(look.nombre_lookup)\r\n" + 
            "FROM \r\n" + 
            "alt_servicio_cliente_lookup as look \r\n" + 
           "WHERE\r\n" + 
            "1=1\r\n" + 
            "AND look.nombre_lookup = '"+nombre+"'\r\n" + 
            "AND look.tipo_lookup='Arreglo'\r\n" + 
            "AND look.id_lookup != "+idLookup).getSingleResult().toString();
        if ( re.equals("0")){
            return false; // no existe
        }else{
            
            return true; // si existeS
        }
    }

}

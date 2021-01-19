package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.repository.ProduccionExplosionProcesosRepository;

@Service
@SuppressWarnings("unchecked")
public class ProduccionExplosionProcesosServiceImpl implements IProduccionExplosionProcesosService {

	@Autowired
	private ProduccionExplosionProcesosRepository repository;
	
	@Autowired
	private EntityManager em;

	
	@Override
	public void save(ProduccionExplosionProcesos produccionExplosionProcesos) {
		// TODO Auto-generated method stub
		repository.save(produccionExplosionProcesos);
		
	}

	@Override
	public ProduccionExplosionProcesos findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@Override
	public List<ProduccionExplosionProcesos> findProgramas() {
		// TODO Auto-generated method stub
		return em.createQuery("FROM ProduccionExplosionProcesos GROUP BY programa").getResultList();
	}
	
	@Override
	public List<Object[]> findAllByPrograma(String programa){
		return em.createNativeQuery("SELECT coor.id_pedido, \r\n" + 
									"		coorPrenda.id_coordinado_prenda, \r\n" + 
									"		coorPrenda.id_coordinado, \r\n" + 
									"		coorPrenda.id_prenda, \r\n" + 
									"		coorPrenda.programa, \r\n" + 
									"		coorPrenda.id_ruta, \r\n" + 
									"		lookupProceso.id_lookup AS id_proceso, \r\n" + 
									"		lookupRuta.nombre_lookup AS Nombre_ruta, \r\n" + 
									"		lookupProceso.nombre_lookup AS Nombre_proceso, \r\n" + 
									"		coor.id_coordinado AS Nombre_proceso \r\n" + 
									"   \r\n" + 
									"FROM alt_comercial_coordinado_prenda AS coorPrenda \r\n" + 
									"   \r\n" + 
									"INNER JOIN alt_comercial_coordinado coor ON coor.id_coordinado = coorPrenda.id_coordinado \r\n" + 
									"INNER JOIN alt_produccion_lookup lookupRuta ON coorPrenda.id_ruta = lookupRuta.id_lookup \r\n" + 
									"INNER JOIN alt_produccion_proceso_ruta procesoRuta ON procesoRuta.id_lookup_ruta = lookupRuta.id_lookup \r\n" + 
									"LEFT JOIN alt_produccion_lookup lookupProceso ON lookupProceso.id_lookup = procesoRuta.id_lookup_proceso \r\n" + 
									"   \r\n" + 
									"WHERE coorPrenda.programa = '"+programa+"' \r\n" + 
									"   \r\n" + 
									"ORDER BY id_prenda, id_ruta, id_proceso").getResultList();
	}
	
	@Override
	public List<ProduccionExplosionProcesos> listExplosionByProceso(Long idProceso){
		
		return em.createNativeQuery("call alt_pr_listar_explosiones_por_proceso("+idProceso+")").getResultList();
	}
 	
}

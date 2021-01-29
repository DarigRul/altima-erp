package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ProduccionExplosionPrendas;
import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.repository.ProduccionExplosionProcesosRepository;

@Service
@SuppressWarnings("unchecked")
public class ProduccionExplosionProcesosServiceImpl implements IProduccionExplosionProcesosService {

	@Autowired
	private ProduccionExplosionProcesosRepository repository;
	
	@Autowired
	private EntityManager em;

	@Transactional
	@Override
	public void save(ProduccionExplosionProcesos produccionExplosionProcesos) {
		// TODO Auto-generated method stub
		repository.save(produccionExplosionProcesos);
		
	}

	@Transactional
	@Override
	public ProduccionExplosionProcesos findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@Transactional
	@Override
	public List<ProduccionExplosionProcesos> findProgramas() {
		// TODO Auto-generated method stub
		return em.createQuery("FROM ProduccionExplosionProcesos GROUP BY programa").getResultList();
	}
	
	@Transactional
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
									"		lookupProceso.nombre_lookup AS Nombre_proceso \r\n" + 
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
	
	@Transactional
	@Override
	public List<ProduccionExplosionProcesos> listExplosionByProceso(Long idProceso){
		
		return em.createNativeQuery("call alt_pr_listar_explosiones_por_proceso("+idProceso+")").getResultList();
	}
 	
	//Métodos para el modal de explosion de prendas en la pantalla de control de avances
	
	@Transactional
	@Override
	public void saveExplosionPrendas (ProduccionExplosionPrendas explosionPrendas){
		if(explosionPrendas.getIdExplosionPrenda() !=null &&  explosionPrendas.getIdExplosionPrenda()>0) {
			em.merge(explosionPrendas);

		}
		else {
			em.persist(explosionPrendas);
		}
	}
	
	@Transactional
	@Override
	public List<Object[]> prendasExplosionarByProceso (Long idExplosionProceso){
		
		return em.createNativeQuery("SELECT explosionP.id_explosion_procesos,\r\n" + 
									"		pedInfo.id_pedido_informacion,\r\n" + 
									"		coorPrenda.id_coordinado_prenda,\r\n" + 
									"		concenPrenda.id_concentrado_prenda,\r\n" + 
									"		concenTallas.id,\r\n" + 
									"		CONCAT(prodLookup.nombre_lookup, producLookup.nombre_lookup) AS Talla \r\n" + 
									"		\r\n" + 
									"FROM alt_produccion_explosion_procesos AS explosionP \r\n" + 
									"		\r\n" + 
									"INNER JOIN alt_comercial_pedido_informacion pedInfo ON explosionP.id_pedido = pedInfo.id_pedido_informacion\r\n" + 
									"INNER JOIN alt_disenio_prenda prenda ON explosionP.clave_prenda = prenda.id_prenda\r\n" + 
									"INNER JOIN alt_comercial_coordinado_prenda coorPrenda ON explosionP.coordinado = coorPrenda.id_coordinado_prenda\r\n" + 
									"INNER JOIN alt_comercial_coordinado coor ON coorPrenda.id_coordinado = coor.id_coordinado\r\n" + 
									"INNER JOIN alt_comercial_concetrado_prenda concenPrenda ON coorPrenda.id_coordinado_prenda = concenPrenda.id_coordinado_prenda\r\n" + 
									"INNER JOIN alt_comercial_concentrado_tallas concenTallas ON pedInfo.id_pedido_informacion = concenTallas.id_pedido AND concenPrenda.id_empleado = concenTallas.id_empleado_pedido\r\n" + 
									"INNER JOIN alt_comercial_cliente_empleado empleado ON concenTallas.id_empleado_pedido = empleado.id_empleado\r\n" + 
									"INNER JOIN alt_produccion_lookup producLookup ON concenTallas.id_largo = producLookup.id_lookup\r\n" + 
									"INNER JOIN alt_produccion_lookup prodLookup ON concenTallas.id_talla = prodLookup.id_lookup\r\n" + 
									"INNER JOIN alt_disenio_tela tela ON coorPrenda.id_tela = tela.id_tela\r\n" + 
									"		\r\n" + 
									"WHERE explosionP.id_explosion_procesos = "+idExplosionProceso+" \r\n" + 
									"		\r\n" + 
									"GROUP BY concenPrenda.id_concentrado_prenda").getResultList();
	}
	
	@Transactional
	@Override
	public List<ProduccionExplosionPrendas> listarPrendasByExplosionProceso(Long idExplosionProceso){
		
		return em.createQuery("FROM ProduccionExplosionPrendas WHERE idExplosionProceso ="+idExplosionProceso).getResultList();
	}
	
}

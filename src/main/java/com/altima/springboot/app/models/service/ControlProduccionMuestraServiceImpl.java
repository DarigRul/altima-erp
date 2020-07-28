package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ControlHora;
import com.altima.springboot.app.models.entity.ControlProduccionMuestra;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.ControlHoraRepository;
import com.altima.springboot.app.repository.ControlProduccionMuestraRepository;

@Service
public class ControlProduccionMuestraServiceImpl implements IControlProduccionMuestraService {
	// vhhbj
	// cgvhbjnk
	@Autowired
	private ControlProduccionMuestraRepository repository;
	
	@Autowired
	private ControlHoraRepository repositoryHora;
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ControlProduccionMuestra> findAll(Long id) {
		
		return em.createQuery("from DisenioControlProduccionMuestra where id_pedido=" + id).getResultList();
	}
	
	@Override
	public void save(ControlProduccionMuestra DisenioControlProduccionMuestra) {
		// TODO Auto-generated method stub
		repository.save(DisenioControlProduccionMuestra);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public ControlProduccionMuestra findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	
	@Override
	public ControlProduccionMuestra findOne2(String id) {
		// TODO Auto-generated method stub
		return repository.findById(Long.parseLong(id)).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> Operadores() {
		// TODO Auto-generated method stub
		
		/*List<Object[]> re = em.createNativeQuery(
				"select empleado.id_empleado,  CONCAT( persona.nombre_persona,' ',  persona.apellido_paterno) as nombre from alt_hr_empleado as empleado, alt_hr_persona as persona \r\n"
						+ "				where 1=1\r\n" + "				and empleado.id_persona= persona.id_persona\r\n"
						+ "				and empleado.id_puesto=1\r\n" + "                ORDER BY persona.nombre_persona")
				.getResultList();
		return re;*/
		
		List<Object[]> re = em.createNativeQuery("select \r\n" + 
				"empleado.id_empleado,  CONCAT(empleado.nombre_persona,' ',empleado.apellido_paterno) as nombre \r\n" + 
				"from \r\n" + 
				"alt_hr_empleado as empleado,\r\n" + 
				"alt_hr_puesto as puesto\r\n" + 
				"where \r\n" + 
				"1=1\r\n" + 
				"AND empleado.id_puesto= puesto.id_puesto\r\n" + 
				"AND puesto.nombre_puesto='Operario'\r\n" + 
				"ORDER BY  empleado.nombre_persona")
		.getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> Operaciones(Long id, String tipo) {
		
		
		
		List<Object[]> re = em.createNativeQuery("select \r\n" + 
				"		CONCAT (empleado.nombre_persona ,'-> ', orden.id_text) , \r\n" + 
				"		Date_format(muestra.fecha_recepcion,'%Y/%M/%d %h:%i:%s %p'),\r\n" + 
				"		Date_format(muestra.fecha_entrega,'%Y/%M/%d %h:%i:%s %p'), \r\n" + 
				"		TIMESTAMPDIFF(DAY, muestra.fecha_recepcion, muestra.fecha_entrega), \r\n" + 
				"		muestra.id_control_produccion_muestra, \r\n" + 
				"		muestra.estatus_tiempo, \r\n" + 
				"		CASE\r\n" + 
				"		WHEN orden.estatus_confeccion = 0 THEN\r\n" + 
				"		\"En espera\" \r\n" + 
				"		WHEN orden.estatus_confeccion = 1 THEN\r\n" + 
				"		\"Terminado\" \r\n" + 
				"		WHEN orden.estatus_confeccion = 2 THEN\r\n" + 
				"		\"Aceptado\" \r\n" + 
				"		WHEN orden.estatus_confeccion = 3 THEN\r\n" + 
				"		\"Rechazado\" ELSE \"***\" \r\n" + 
				"	END  , \r\n" + 
				"	orden.id_detalle_pedido \r\n" + 
				"		from \r\n" + 
				"		alt_hr_empleado as empleado, \r\n" + 
				"		alt_control_produccion_muestra  as muestra, \r\n" + 
				"		alt_produccion_detalle_pedido as orden \r\n" + 
				"		where \r\n" + 
				"		1=1\r\n" + 
				"		and empleado.id_empleado=muestra.id_operario			\r\n" + 
				"		and muestra.tipo=" + tipo + " \r\n"+
				"		and muestra.id_pedido = orden.id_detalle_pedido 		\r\n" + 
				"		and orden.id_pedido="+id)
				.getResultList();
		return re;
		
	}
	
	
	
	
	
	
	
	
	@Override
	public void saveHora(ControlHora DisenioControlHora) {
		// TODO Auto-generated method stub
		repositoryHora.save(DisenioControlHora);
		
	}
	
	@Override
	public ControlHora findOneHora(Long id) {
		// TODO Auto-generated method stub
		return repositoryHora.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Integer Pausa(Long id) {
		Integer re = (Integer) em
				.createNativeQuery("select   MAX(alt_control_hora.id_control_hora) from alt_control_hora\r\n"
						+ "where  alt_control_hora.estatus=\"Play\"\r\n" + "and  alt_control_hora.id_control_produccion_muestra="
						+ id)
				.getSingleResult();
		return re;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ContadorHoras(Long id, String tipo) {
		List<Object[]> re = em.createNativeQuery("SELECT 	hora.id_control_hora, \r\n"
				+ "						Date_format( hora.fecha_inicio,'%Y/%M/%d %h:%i:%s %p'), \r\n"
				+ "						IFNULL(Date_format(	hora.fecha_fin,'%Y/%M/%d %h:%i:%s %p'),'En proceso') ,  \r\n"
				+ "											CASE\r\n" + "												WHEN hora.tipo = 1 THEN 'Trazo' \r\n"
				+ "												WHEN hora.tipo = 2 THEN 'Corte' \r\n"
				+ "												WHEN hora.tipo = 3 THEN 'Confección' \r\n"
				+ "												WHEN hora.tipo = 4 THEN 'Planchado' \r\n"
				+ "												WHEN hora.tipo = 5 THEN 'Terminado' \r\n" + "												ELSE 'Null'  \r\n"
				+ "												END  , IFNULL(\r\n"
				+ "								                             IF( TIMESTAMPDIFF (HOUR,hora.fecha_inicio, hora.fecha_fin )  =0,\r\n"
				+ "								                             CONCAT( TIMESTAMPDIFF (  MINUTE,hora.fecha_inicio, hora.fecha_fin ),' Minutos'), \r\n"
				+ "								                             CONCAT( TIMESTAMPDIFF (HOUR,hora.fecha_inicio, hora.fecha_fin ),' Horas')),'En proceso') as tiempo,\r\n"
				+ "							hora.estatus \r\n" + "							from \r\n"
				+ "				            alt_control_hora as hora\r\n" + "							where 1=1\r\n"
				+ "							and hora.tipo=" + tipo + "\r\n"
				+ "                         	and hora.id_control_produccion_muestra=" + id).getResultList();
		return re;
	}
	
	// njjjj
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ListarPedidos() {
		
		
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	pedido.id_pedido,\r\n" + 
				"	pedido.id_text,\r\n" + 
				"	pedido.fecha_creacion,\r\n" + 
				"	pedido.descripcion,\r\n" + 
				"	(\r\n" + 
				"		IFNULL(\r\n" + 
				"			(\r\n" + 
				"			SELECT\r\n" + 
				"				(\r\n" + 
				"				CASE\r\n" + 
				"						\r\n" + 
				"						WHEN muestra.tipo = 1 THEN\r\n" + 
				"						'Trazo' \r\n" + 
				"						WHEN muestra.tipo = 2 THEN\r\n" + 
				"						'Corte' \r\n" + 
				"						WHEN muestra.tipo = 3 THEN\r\n" + 
				"						'Confección' \r\n" + 
				"						WHEN muestra.tipo = 4 THEN\r\n" + 
				"						'Planchado' \r\n" + 
				"						WHEN muestra.tipo = 5 THEN\r\n" + 
				"						'Terminado' ELSE 'Nuevo' \r\n" + 
				"					END \r\n" + 
				"					) \r\n" + 
				"				FROM\r\n" + 
				"					alt_control_produccion_muestra AS muestra,\r\n" + 
				"					alt_produccion_detalle_pedido AS detalle \r\n" + 
				"				WHERE\r\n" + 
				"					1 = 1 \r\n" + 
				"					AND pedido.id_pedido = detalle.id_pedido \r\n" + 
				"					AND detalle.id_detalle_pedido = muestra.id_pedido \r\n" + 
				"					AND muestra.estatus_tiempo = 'Play' \r\n" + 
				"					LIMIT 1 \r\n" + 
				"				),\r\n" + 
				"				'Sin proceso asignado' \r\n" + 
				"			) \r\n" + 
				"		) AS proceso \r\n" + 
				"	FROM\r\n" + 
				"		alt_produccion_pedido AS pedido \r\n" + 
				"	WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND pedido.estatus_pedido =1"
				+ " ORDER BY pedido.fecha_creacion DESC").getResultList();
		return re;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Integer Contador(String tipo) {
		
		String re = em.createNativeQuery("SELECT COUNT(*) " + "FROM alt_control_produccion_muestra "
				+ "WHERE alt_control_produccion_muestra.tipo=" + tipo).getSingleResult().toString();
		return Integer.parseInt(re);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findAllPrenda() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipo_lookup='Familia Prenda'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Object[]> PausarMuestras(Long id, String tipo) {
		
		List<Object []> re = em.createNativeQuery("SELECT   muestra.id_control_produccion_muestra FROM alt_control_produccion_muestra as muestra , alt_produccion_detalle_pedido as pedido \r\n" + 
				"WHERE 1=1\r\n" + 
				"and muestra.id_pedido= pedido.id_detalle_pedido\r\n" + 
				"and muestra.estatus_tiempo='Play'\r\n" + 
				"and muestra.tipo="+tipo+"\r\n" + 
				"and pedido.id_pedido="+id).getResultList();
		
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> generos() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipo_lookup='Familia Genero'").getResultList();
	}
	
	
	
}

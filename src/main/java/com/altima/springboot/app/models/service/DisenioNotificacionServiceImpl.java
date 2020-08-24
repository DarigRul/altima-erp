package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioNotificacion;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.repository.CatalogoRepository;
import com.altima.springboot.app.repository.DisenioNotificacionRepository;

@Service
public class DisenioNotificacionServiceImpl implements IDisenioNotificacionService{
	@PersistenceContext
	EntityManager em;
    @Autowired
	private DisenioNotificacionRepository repository;
	
	@Override
	@Transactional
	public Usuario findBynombreUsuario(String username) {
		return (Usuario) em.createQuery("from Usuario where nombreUsuario = '"+username+"'").getSingleResult();		
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<DisenioNotificacion> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioNotificacion>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioNotificacion disenionotificacion) {
		// TODO Auto-generated method stub
		repository.save(disenionotificacion);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioNotificacion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllWithApplicantName() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT adn.id_notificacion,adn.folio,adn.asunto,adn.fecha,query.nombre,query.solicitante\n" + 
				",adn.id_solicitud,adn.estatus FROM `alt_disenio_notificacion` adn,(SELECT\n" + 
				"p.id_cliente,\n" + 
				"    s.nombre,\n" + 
				"    p.id_pedido,\n" + 
				"   CONCAT(he.nombre_persona,' ',he.apellido_paterno,' ',he.apellido_materno) as solicitante\n" + 
				"FROM\n" + 
				"    alt_produccion_pedido p\n" + 
				", alt_comercial_cliente s \n" + 
				",alt_hr_usuario hu\n" + 
				"LEFT JOIN alt_hr_empleado he\n" + 
				"on hu.id_empleado=he.id_empleado\n" + 
				"where s.id_usuario=hu.id_usuario\n" + 
				"and s.id_cliente=p.id_cliente\n" + 
				"\n" + 
				"and\n" + 
				"    p.id_cliente = s.id_cliente) as query\n" + 
				"		where query.id_cliente=adn.cliente\n" + 
				"and query.id_pedido=adn.id_solicitud		\n" + 
				"").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionPedido> findOneNotification(Long solicitud) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(" SELECT\n" + 
				"    p.id_text,\n" + 
				"    s.nombre,\n" + 
				"    p.fecha_entrega,\n" + 
				"    p.id_pedido,\n" + 
				"    p.descripcion,\n" + 
				"    p.fecha_creacion,\n" + 
				"   CONCAT(he.nombre_persona,' ',he.apellido_paterno,' ',he.apellido_materno),\n" + 
				"    p.tipo_pedido,\n" + 
				"	 p.estatus_pedido\n" + 
				"FROM\n" + 
				"    alt_produccion_pedido p\n" + 
				", alt_comercial_cliente s \n" + 
				",alt_hr_usuario hu\n" + 
				"LEFT JOIN alt_hr_empleado he\n" + 
				"on hu.id_empleado=he.id_empleado\n" + 
				"where s.id_usuario=hu.id_usuario\n" + 
				"and s.id_cliente=p.id_cliente\n" + 
				"\n" + 
				"and\n" + 
				"    p.id_cliente = s.id_cliente\n" + 
				"and\n" + 
				"     descripcion !='Foráneo'\n" +
				"   and id_pedido ="+solicitud+"\n" +
				"ORDER BY\n" + 
				"    fecha_creacion\n" + 
				"DESC").getResultList();
	}
	
}

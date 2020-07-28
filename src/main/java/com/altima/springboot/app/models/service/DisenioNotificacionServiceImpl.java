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
		return em.createNativeQuery("SELECT alt_disenio_notificacion.*,alt_hr_usuario.nombre_usuario FROM `alt_disenio_notificacion`,`alt_hr_usuario` where alt_disenio_notificacion.id_solicitante=alt_hr_usuario.id_usuario").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionPedido> findOneNotification(Long solicitud) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(" SELECT\r\n" + 
				"    p.id_text,\r\n" + 
				"    s.nombre_sucursal,\r\n" + 
				"    p.fecha_entrega,\r\n" + 
				"    p.id_pedido,\r\n" + 
				"    p.descripcion,\r\n" + 
				"    p.fecha_creacion,\r\n" + 
				"    p.creado_por,\r\n" +
				"    p.tipo_pedido,\r\n" + 
				"	 p.estatus_pedido\r\n" + 
				"FROM\r\n" + 
				"    alt_produccion_pedido p\r\n" + 
				"INNER JOIN alt_comercial_cliente_sucursal s ON\r\n" + 
				"    p.id_sucursal = s.id_cliente_sucursal\r\n" + 
				"WHERE\r\n" + 
				"     descripcion !='Foráneo'\r\n" + 
				"   and  id_pedido ="+solicitud+"\r\n" + 
				"ORDER BY\r\n" + 
				"    fecha_creacion\r\n" + 
				"DESC").getResultList();
	}
	
}

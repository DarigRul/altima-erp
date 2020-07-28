package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCotizacion;
import com.altima.springboot.app.repository.ComercialCotizacionRepository;

@Service
public class ComercialCotizacionServiceImpl implements IComercialCotizacionService{
	
	@Autowired
	ComercialCotizacionRepository repository;
	@Autowired
	EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<ComercialCotizacion> findAll() {
		return (List<ComercialCotizacion>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(ComercialCotizacion Comercialcotizacion) {
		repository.save(Comercialcotizacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> findAllWithTotal() {
		
		return em.createNativeQuery("SELECT cc.id_cotizacion, cc.id_text, cc.fecha_creacion, cc.tipo_cotizacion, CONCAT(cliente.nombre, ifnull(cliente.apellido_paterno,''), ifnull(cliente.apellido_materno,'')), ct.total, CONCAT(empleado.nombre_persona,' ', empleado.apellido_paterno,' ', empleado.apellido_materno), cc.estatus FROM alt_comercial_cotizacion AS cc \r\n" + 
				"INNER JOIN alt_comercial_cotizacion_total ct ON cc.id_cotizacion = ct.id_cotizacion \r\n" +
				"INNER JOIN alt_comercial_cliente cliente ON cc.id_cliente = cliente.id_cliente \r\n" +
				"INNER JOIN alt_hr_empleado empleado ON cc.id_agente_ventas = empleado.id_empleado\r\n" + 
				"ORDER BY cc.id_text").getResultList();
	}

	@Override
	@Transactional
	public ComercialCotizacion findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public String findMax() {
		return em.createNativeQuery("SELECT MAX(id_cotizacion) FROM alt_comercial_cotizacion").getSingleResult().toString();
	}
	
	
	
	
}

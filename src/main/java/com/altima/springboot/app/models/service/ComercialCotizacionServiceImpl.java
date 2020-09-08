package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCotizacion;
import com.altima.springboot.app.repository.ComercialCotizacionRepository;

@Service
@SuppressWarnings("unchecked")
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

	@Override
	@Transactional
	public List<Object> findAllWithTotal(Long idAgente) {
		if (idAgente==null) {
			return em.createNativeQuery("SELECT cc.id_cotizacion, \n" + 
										"		cc.id_text, \n" + 
										"		cc.fecha_creacion, \n" + 
										"		cc.tipo_cotizacion, \n" + 
										"		CONCAT(cliente.nombre, ifnull(cliente.apellido_paterno,''), \n" + 
										"		ifnull(cliente.apellido_materno,'')), \n" + 
										"		(SELECT SUM(subtotal+descuento_monto+iva_monto) FROM alt_comercial_cotizacion_total\n" + 
										"			WHERE id_cotizacion=cc.id_cotizacion), \n" + 
										"		CONCAT(empleado.nombre_persona,' ', empleado.apellido_paterno,' ', empleado.apellido_materno), \n" + 
										"		cc.estatus, \n" + 
										"		ct.anticipo_porcentaje, \n" +
										"		ct.descuento_porcentaje \n" + 
										"		\n" + 
										"	FROM alt_comercial_cotizacion AS cc\n" + 
										"INNER JOIN alt_comercial_cotizacion_total ct ON cc.id_cotizacion = ct.id_cotizacion\n" + 
										"INNER JOIN alt_comercial_cliente cliente ON cc.id_cliente = cliente.id_cliente \n" + 
										"INNER JOIN alt_hr_empleado empleado ON cc.id_agente_ventas = empleado.id_empleado\n" + 
										"ORDER BY cc.id_cotizacion DESC").getResultList();
		}
		else {
			return em.createNativeQuery("SELECT cc.id_cotizacion, \n" + 
										"		cc.id_text, \n" + 
										"		cc.fecha_creacion, \n" + 
										"		cc.tipo_cotizacion, \n" + 
										"		CONCAT(cliente.nombre, ifnull(cliente.apellido_paterno,''), \n" + 
										"		ifnull(cliente.apellido_materno,'')), \n" + 
										"		(SELECT SUM(subtotal+descuento_monto+iva_monto) FROM alt_comercial_cotizacion_total\n" + 
										"			WHERE id_cotizacion=cc.id_cotizacion), \n" + 
										"		CONCAT(empleado.nombre_persona,' ', empleado.apellido_paterno,' ', empleado.apellido_materno), \n" + 
										"		cc.estatus, \n" + 
										"		ct.anticipo_porcentaje, \n" + 
										"		ct.descuento_porcentaje \n" + 
										"		\n" + 
										"	FROM alt_comercial_cotizacion AS cc\n" + 
										"INNER JOIN alt_comercial_cotizacion_total ct ON cc.id_cotizacion = ct.id_cotizacion\n" + 
										"INNER JOIN alt_comercial_cliente cliente ON cc.id_cliente = cliente.id_cliente \n" + 
										"INNER JOIN alt_hr_empleado empleado ON cc.id_agente_ventas = empleado.id_empleado\n" + 
										"WHERE cc.id_agente_ventas=" +idAgente+ "\r\n" +
										"ORDER BY cc.id_cotizacion DESC").getResultList();
		}
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

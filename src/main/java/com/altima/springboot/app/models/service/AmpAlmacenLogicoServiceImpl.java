package com.altima.springboot.app.models.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.AmpAlmacenLogico;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.repository.AmpAlmacenLogicoRepository;

@Service
public class AmpAlmacenLogicoServiceImpl implements IAmpAlmacenLogicoService{
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
AmpAlmacenLogicoRepository repository;


@Override
@Transactional
public void save(AmpAlmacenLogico entity) {
	repository.save(entity);
}

@Override
@Transactional
public AmpAlmacenLogico findOne(Long id) {
	return repository.findById(id).orElse(null);
}

@Override
@Transactional
public List<AmpAlmacenLogico> findAll() {
	return (List<AmpAlmacenLogico>) repository.findAll();
}

@Override
@Transactional
public void deleteById(Long id) {
	repository.deleteById(id);
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> findAllAMPLogico() {
	return em.createNativeQuery("SELECT al.id_almacen_logico,al.nombre_almacen_logico,af.id_almacen_fisico,af.nombre_almacen,ampls.id_lookup as idsalida,ampls.nombre_lookup as salida\r\n" + 
			",ample.id_lookup as identrada,ample.nombre_lookup as entrada,\r\n" + 
			"al.creado_por,al.fecha_creacion,al.actualizado_por,al.ultima_fecha_modificacion,al.estatus,al.tipo\r\n" + 
			"FROM alt_amp_almacen_logico al, alt_amp_almacen_fisico af, alt_amp_lookup ampls,alt_amp_lookup ample where al.id_almacen_fisico=af.id_almacen_fisico\r\n" + 
			"and \r\n" + 
			"al.id_movimiento_salida=ampls.id_lookup\r\n" + 
			"and\r\n" + 
			"al.id_movimiento_entrada=ample.id_lookup\r\n" + 
			"").getResultList();
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<AmpAlmacenLogico> findAMPLogicoDuplicate(Long AlmacenFisico, String Nombre, Long Entrada, Long Salida) {
	return em.createQuery("From AmpAlmacenLogico where idAlmacenFisico="+AlmacenFisico+" and nombreAlmacenLogico='"+Nombre+"' and idMovimientoEntrada="+Entrada+" and idMovimientoSalida="+Salida+" ").getResultList();
}

@Override
public boolean existHabilitacion() {
	

	try {
		String valos= em.createNativeQuery("SELECT\r\n" + 
				"	logico.nombre_almacen_logico\r\n" + 
				"FROM\r\n" + 
				"	alt_amp_almacen_logico AS logico \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND logico.estatus = 1 \r\n" + 
				"	AND logico.tipo =2").getSingleResult().toString();

	return true;	
	}
		catch(Exception e) {
			
			return false;
		}
	
}

@Override
public boolean existMaterial() {
	

	try {
		String valos= em.createNativeQuery("SELECT\r\n" + 
				"	logico.nombre_almacen_logico\r\n" + 
				"FROM\r\n" + 
				"	alt_amp_almacen_logico AS logico \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND logico.estatus = 1 \r\n" + 
				"	AND logico.tipo =3").getSingleResult().toString();
		
	return true;	
	}
		catch(Exception e) {
			return false;
		
	
}
}
	
}

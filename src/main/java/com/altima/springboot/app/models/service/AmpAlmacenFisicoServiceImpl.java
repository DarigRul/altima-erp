package com.altima.springboot.app.models.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.AmpAlmacenFisico;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.repository.AmpAlmacenFisicoRepository;

@Service
public class AmpAlmacenFisicoServiceImpl implements IAmpAlmacenFisicoService{
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
AmpAlmacenFisicoRepository repository;


@Override
@Transactional
public void save(AmpAlmacenFisico entity) {
	repository.save(entity);
}

@Override
@Transactional
public AmpAlmacenFisico findOne(Long id) {
	return repository.findById(id).orElse(null);
}

@Override
@Transactional
public List<AmpAlmacenFisico> findAll() {
	return (List<AmpAlmacenFisico>) repository.findAll();
}

@Override
@Transactional
public void deleteById(Long id) {
	repository.deleteById(id);
}
	
@SuppressWarnings("unchecked")
@Override
@Transactional
public List<HrEmpleado> findEmployeeAMP(){
	return em.createQuery("Select idEmpleado, concat(nombrePersona,' ',apellidoPaterno,' ',apellidoMaterno) From HrEmpleado where idPuesto IN(32,18) AND estatus='1'").getResultList();
	
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> findAllAMPFisico(){
	return em.createNativeQuery("SELECT CONCAT(he.nombre_persona,' ',he.apellido_paterno,' ',he.apellido_materno) ,af.* FROM alt_amp_almacen_fisico af,alt_hr_empleado he where af.id_empleado=\r\n" + 
			"he.id_empleado;").getResultList();
	
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<AmpAlmacenFisico>findAMPFisicoDuplicate(String Nombre, Long Encargado){
	return em.createQuery("From AmpAlmacenFisico where nombreAlmacen='"+Nombre+"' and idEmpleado="+Encargado+"").getResultList();
	
}



}

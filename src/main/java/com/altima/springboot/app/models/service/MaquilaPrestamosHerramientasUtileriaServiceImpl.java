package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.MaquilaPrestamosHerramientasUtileria;
import com.altima.springboot.app.repository.MaquilaPrestamosHerramientasUtileriaRepository;

@Service
public class MaquilaPrestamosHerramientasUtileriaServiceImpl implements IMaquilaPrestamosHerramientasUtileriaService{

	@Autowired
	MaquilaPrestamosHerramientasUtileriaRepository repository;
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	@Override
	public void save(MaquilaPrestamosHerramientasUtileria maquilaprestamosherramientasutileria) {
		repository.save(maquilaprestamosherramientasutileria);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]>ListarPrestamosHerramientas() {
		
		
		 return em.
		 createNativeQuery("SELECT amphu.*,CONCAT(ahe.nombre_persona,' ',ahe.apellido_paterno,' ',ahe.apellido_materno) FROM `alt_maquila_prestamos_herramientas_utileria` amphu INNER JOIN\r\n"
		 + "alt_hr_empleado ahe\r\n" + "where amphu.id_operario=ahe.id_empleado\r\n" +
		 "GROUP BY amphu.folio").getResultList();
		 
		
		
		
	}
}

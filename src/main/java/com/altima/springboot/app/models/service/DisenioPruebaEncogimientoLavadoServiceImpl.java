package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;
import com.altima.springboot.app.repository.DisenioPruebaEncogimientoLavadoRepository;

@Service
public class DisenioPruebaEncogimientoLavadoServiceImpl implements IDisenioPruebaEncogimientoLavadoService {

	@Autowired
	private DisenioPruebaEncogimientoLavadoRepository repository;
	
	@Autowired
	private EntityManager em;
	@Override
	public List<DisenioPruebaEncogimientoLavado> findAll(DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado) {

		repository.save(pruebaEncogimientoLavado);
	}

	@Override
	public int ifExist(Long id) {
		
		return Integer.parseInt(em.createNativeQuery("SELECT IF((SELECT COUNT(*)\r\n" + 
				" FROM alt_disenio_prueba_encogimiento_lavado \r\n" + 
				"	WHERE id_calidad = "+id+")>0, 1 , 0)").getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioPruebaEncogimientoLavado> findAllByCalidad(Long id) {
		
		return em.createQuery("FROM DisenioPruebaEncogimientoLavado \r\n" + 
				"	WHERE idCalidad ="+id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public DisenioPruebaEncogimientoLavado findByTipoPrueba(String tipo, Long id) {

		return (DisenioPruebaEncogimientoLavado) em.createQuery("from DisenioPruebaEncogimientoLavado where idCalidad="+id+" and tipoPrueba = '"+tipo+"'").getSingleResult();
	}

	@Override
	public int ifExistLavado(Long id, String tipo) {
		
		return Integer.parseInt(em.createNativeQuery("SELECT IF((SELECT COUNT(*)\r\n" + 
				" FROM alt_disenio_prueba_encogimiento_lavado \r\n" + 
				"	WHERE id_calidad = "+id+" AND tipo_prueba = '"+tipo+"')>0, 1 , 0)").getSingleResult().toString());
	}

	@Override
	@Transactional(readOnly = true)
	public Long findEntretelaByIdTela(Long id) {
		// TODO Auto-generated method stub
		return Long.parseLong( em.createNativeQuery(
				"SELECT IFNULL(max(adpel.entretela_prueba_vapor),0) FROM alt_disenio_prueba_encogimiento_lavado adpel INNER JOIN alt_disenio_calidad adc ON adc.id_calidad=adpel.id_calidad WHERE adc.estatus=2 AND adc.id_material=:id AND adc.tipo_material=1")
				.setParameter("id", id).getSingleResult().toString());
	}


}

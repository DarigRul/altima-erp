package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ProduccionMaquilador;
import com.altima.springboot.app.repository.ProduccionMaquiladorRepository;

@Service
public class ProduccionMaquiladorServiceImpl implements IProduccionMaquiladorService {

    @PersistenceContext
	private EntityManager em;
	
	@Autowired
    private ProduccionMaquiladorRepository repository;
    

    @Override
	@Transactional(readOnly=true)
    public List<ProduccionMaquilador> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionMaquilador>) repository.findAll();
    }

    @Override
	@Transactional
    public void save(ProduccionMaquilador maquilador) {
        // TODO Auto-generated method stub
        repository.save(maquilador);

    }

    @Override
	@Transactional
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @Override
	@Transactional
    public ProduccionMaquilador findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> findAllCompleto() {
        List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n"+
                "ma.id_maquilador,\r\n"+
                "ma.id_text,\r\n"+
                "ma.nombre,\r\n"+
                "ma.tipo,\r\n"+
                "ma.descripcion,\r\n"+
                "ma.produccion_maxima,\r\n"+
                "look.nombre_lookup,\r\n"+
				"ma.estatus,\r\n"+
				"ma.creado_por,\r\n"+
				"DATE_FORMAT(	ma.fecha_creacion,'%Y-%m-%d %T'),\r\n"+
				"ma.actualizado_por,\r\n"+
				"DATE_FORMAT(	ma.ultima_fecha_modificacion,'%Y-%m-%d %T'), look.id_lookup\r\n"+
			"FROM\r\n"+
				"alt_produccion_maquilador AS ma\r\n"+
				"INNER JOIN alt_produccion_lookup look ON ma.id_ubicacion = look.id_lookup ").getResultList();

		return re;

    }

}

package com.altima.springboot.app.models.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;

@Service
public class ProduccionCalendarioServiceImpl implements IProduccionCalendarioService {
    @PersistenceContext
	private EntityManager em;

    @Transactional(readOnly = true)
	@Override
	public Integer validarAnio(String anio) {
        String re = em.createNativeQuery(""+
                    "SELECT\n" + 
                    "COUNT( fecha ) \n" + 
                "FROM\n" + 
                    "alt_produccion_calendario \n" + 
                "WHERE\n" + 
                    "YEAR ( fecha )= "+anio).getSingleResult().toString();
	
		return Integer.parseInt(re);
		
	}

    @Override
	@Transactional
    public void crearCalendario(String fecha_incial, String fecha_final) {

        Query query = em.createNativeQuery("call proc_pa_sumar_fechas('"+fecha_incial+"','"+fecha_final+"');");
		query.executeUpdate();

    }

}

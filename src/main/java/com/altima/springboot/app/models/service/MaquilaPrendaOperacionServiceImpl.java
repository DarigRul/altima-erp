package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.MaquilaLookup;
import com.altima.springboot.app.models.entity.MaquilaPrendaOperacion;
import com.altima.springboot.app.repository.MaquilaLookupRepository;
import com.altima.springboot.app.repository.MaquilaPrendaOperacionRepository;

@Service
public class MaquilaPrendaOperacionServiceImpl implements IMaquilaPrendaOperacionService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MaquilaPrendaOperacionRepository repository;

    @Override
	@Transactional
    public void save(MaquilaPrendaOperacion lookup) {
        repository.save(lookup);

    }

    @Override
	@Transactional
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view(Long id) {
    	List<Object[]> re = null;
		re = em.createNativeQuery(""
				+ "SELECT \r\n" + 
				"PO.id_asignacion,\r\n" + 
				"(@i\\:=@i+1) as 'contador', \r\n" + 
				"familia.nombre_lookup as familia,\r\n" + 
				"operacion.nombre_lookup as operacion,\r\n" + 
				"operacion.atributo_2 as sam,\r\n" + 
				"ROUND((60/ operacion.atributo_2)) as hrs,\r\n" + 
				"ROUND(((60/ operacion.atributo_2)* operacion.atributo_3)) as turno\r\n" +
				"FROM\r\n" + 
				"	alt_maquila_prenda_operacion AS PO\r\n" + 
				"	INNER JOIN alt_maquila_lookup operacion ON operacion.id_lookup = PO.id_operacion\r\n" + 
				"	INNER JOIN alt_maquila_lookup familia ON operacion.descripcion_lookup = familia.id_lookup\r\n" + 
				"	cross join (select @i \\:= 0) r \r\n" + 
				"	WHERE \r\n" + 
				"	1=1\r\n" + 
				"	AND PO.id_prenda="+id).getResultList();

	return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> SelectOperacion(Long famila, Long prenda) {
        List<Object[]> re = null;
		re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	operacion.id_lookup,\r\n" + 
				"	operacion.nombre_lookup\r\n" + 
				"FROM\r\n" + 
				"alt_maquila_lookup as operacion\r\n" + 
				"INNER JOIN alt_maquila_lookup familia on operacion.descripcion_lookup = familia.id_lookup\r\n" + 
				"WHERE\r\n" + 
				"1=1\r\n" + 
				"and operacion.tipo_lookup='Operacion'\r\n" + 
				"AND familia.tipo_lookup='Familia'\r\n" + 
                "AND familia.id_lookup="+famila+"\r\n" + 
                "AND operacion.estatus=1\r\n" + 
				"AND operacion.id_lookup not in (SELECT PO.id_operacion FROM alt_maquila_prenda_operacion AS PO WHERE PO.id_prenda="+prenda+")").getResultList();

	return re;
	}
    
}

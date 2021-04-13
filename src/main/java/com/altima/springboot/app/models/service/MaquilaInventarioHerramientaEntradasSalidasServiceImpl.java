package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.MaquilaInventarioHerramientasEntradasSalidas;
import com.altima.springboot.app.models.entity.MaquilaLookup;
import com.altima.springboot.app.repository.MaquilaInventarioHerramientaEntradasSalidasRepository;

@Service
public class MaquilaInventarioHerramientaEntradasSalidasServiceImpl implements IMaquilaInventarioHerramientaEntradasSalidasService {

	@PersistenceContext
	 EntityManager em;
	@Autowired
	MaquilaInventarioHerramientaEntradasSalidasRepository repository;
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listaMovimientos(){
	 return	em.createNativeQuery("select aml.id_lookup as id_articulo,aml.nombre_lookup as articulo,aml2.nombre_lookup as concepto,aml2.descripcion_lookup as movimiento \r\n"
				+ ",amihes.*,aml.descripcion_lookup\r\n"
				+ "from alt_maquila_lookup aml\r\n"
				+ "INNER JOIN alt_maquila_lookup aml2\r\n"
				+ "on aml.tipo_lookup=\"Herramientas\"\r\n"
				+ "and aml2.tipo_lookup=\"MAF\"\r\n"
				+ "INNER JOIN alt_maquila_inventario_herramienta_entradas_salidas amihes\r\n"
				+ "on amihes.id_movimiento=aml2.id_lookup\r\n"
				+ "and amihes.id_herramienta=aml.id_lookup\r\n"
				+ "where amihes.id_movimiento not in(1,2)").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MaquilaLookup> listaConceptos(String descripcion){
		
		return em.createQuery("From MaquilaLookup WHERE tipoLookup='MAF' AND descripcionLookup='"+descripcion+"' and estatus=1").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MaquilaLookup> listaArticulos() {
		// TODO Auto-generated method stub
		return em.createQuery("From MaquilaLookup WHERE tipoLookup='Herramientas'  and estatus=1").getResultList();
	}

	@Override
	@Transactional
	public void save(MaquilaInventarioHerramientasEntradasSalidas mihes) {
		// TODO Auto-generated method stub
		repository.save(mihes);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
		
	}
	
}

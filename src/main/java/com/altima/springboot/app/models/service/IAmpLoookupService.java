package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.AmpLookup;

public interface IAmpLoookupService {

	List<AmpLookup> findAll();

	void save(AmpLookup lookup);

	void delete(Long id);

	AmpLookup findOne(Long id);

	boolean findDuplicate(String Lookup,String Tipo);

	List<AmpLookup> findAllLookup(String Tipo);

	AmpLookup findLastLookupByType(String Tipo);

	boolean findDuplicate(String Lookup, String Tipo, String atributo);

	List<Object []> listarLinea(Long id);
	
	String nombreCategoria(Long id);

	List<AmpLookup> findAllMovements();

	List<AmpLookup> findMovementsDuplicate(String movimiento, String tipo);
	
	List<Object []> findAllLinea();
	
	List<Object []> findAllAlmacen();
	
	List<Object []> findAllPasillos();
	
}

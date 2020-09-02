package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioLookup;

public interface ICatalogoService {

	List<DisenioLookup> findAll();

	void save(DisenioLookup diseniolookup);

	void delete(Long id);

	DisenioLookup findOne(Long id);

	boolean findDuplicate(String Lookup,String Tipo);

	List<DisenioLookup> findAllLookup(String Tipo);

	DisenioLookup findLastLookupByType(String Tipo);

	boolean findDuplicate(String Lookup, String Tipo, String atributo);
	
	List<Object []> findAllMaterialClasificacion();

	boolean findDuplicatePrecioComposicion(Long idPrenda, Long idFamComposicion);


}

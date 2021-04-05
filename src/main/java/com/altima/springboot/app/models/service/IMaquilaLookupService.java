package com.altima.springboot.app.models.service;


import java.util.List;
import com.altima.springboot.app.models.entity.MaquilaLookup;
public interface IMaquilaLookupService {

	List<MaquilaLookup> findAll();
	
	List<MaquilaLookup> findAll(String tipo, String estatus);

	void save(MaquilaLookup lookup);

	void delete(Long id);

	MaquilaLookup findOne(Long id);

	boolean findDuplicate(String Lookup,String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 );

	List<MaquilaLookup> findAllLookup(String Tipo);

	MaquilaLookup findLastLookupByType(String Tipo);

	boolean findDuplicate(String Lookup, String Tipo, String atributo);

	List<Object []> listarActivos();
	
	List<Object []> listarColor();

	List<Object []> listarFamiliabyMaquinaria();

	List<Object []> Operaciones();
	
	boolean findDuplicateMaquila(String lookup, String tipo);

	boolean findDuplicateMaquila(String lookup, String tipo, String descripcion);
}

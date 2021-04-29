package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.SoporteTecnicoLookup;

public interface ISoporteTecnicoLookupService {

	List<SoporteTecnicoLookup> findAllByType(String Tipo);

	void save(SoporteTecnicoLookup SoporteTecnicoLookup);

	void delete(Long id);

	SoporteTecnicoLookup findOne(Long id);

	boolean findDuplicate(String Lookup,String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 );

	SoporteTecnicoLookup findLastLookupByType(String Tipo);

	List<SoporteTecnicoLookup> findAllLookup(String Tipo);

	List<Object[]> findAllPrendas();

	//
	List<SoporteTecnicoLookup> listaProcesos();
}

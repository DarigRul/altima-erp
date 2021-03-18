package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteArregloPrenda;
import com.altima.springboot.app.models.entity.ServicioClienteLookup;

public interface IServicioClienteLookupService {

	List<ServicioClienteLookup> findAllByType(String Tipo);

	void save(ServicioClienteLookup servicioclientelookup);

	void delete(Long id);

	ServicioClienteLookup findOne(Long id);

	boolean findDuplicate(String Lookup,String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 );

	ServicioClienteLookup findLastLookupByType(String Tipo);

	List<ServicioClienteLookup> findAllLookup(String Tipo);

	List<Object[]> findAllPrendas();

	// muchos a muchos de arreglo prenda
	void saveArregloPrenda(ServicioClienteArregloPrenda obj);
	void deleteArregloPrenda(Long id);
	List<Object[]> verPrendaArreglo(Long id);
	boolean findOnePrendaArreglo(Long idArreglo, String idPrenda,String idComplejidad);
	boolean validarNombreArregloEditar(Long idLookup, String nombre);

}

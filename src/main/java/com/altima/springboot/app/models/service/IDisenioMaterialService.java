package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;


public interface IDisenioMaterialService {

	List<DisenioMaterial> findAll();

	void save(DisenioMaterial diseniomaterial) throws DataIntegrityViolationException;

	void delete(Long id);

	DisenioMaterial findOne(Long id);
	
	List<DisenioLookup> findListaLookupMat();
	
	List<DisenioLookup> findListaLookupMed();
	
	List<DisenioLookup> findListaMarcas();
	
	List<DisenioLookup> findListaClasificacion();
	
	List <Object []> disenioMaterial ();
	
	List <Object []> disenioMaterialFiltro();

	List<DisenioLookup> findLookUps();
	
	Object findLookUp(Long id);
	
	Object findUno(Long id);
	
	List<DisenioMaterial> findAllForCreate();

	List<DisenioLookup> findListaColor();
	
	List<DisenioMaterial> findAllFromPrenda(Long id);
	
	List<DisenioLookup> findAllPatronajeFromPrenda(Long id);
	
	List<DisenioLookup> findAllFamiliaPrenda();
	
	List<DisenioLookup> findListaLookupPro();
	
	List<Object> findAllByTipoMaterial(Long id);

	Object findByIdMaterial(Long id);

	List<Object[]> findLastMaterial(Long tipo_material);
	
	public int count(Long id);

	public int count2(Long id);

	public String findunique(Long id);
	
	DisenioMaterial findMaterialPorId(Long id);
	
	public String Exist2(String comparacion);
	
	public String Exist3(Long id);
	
	//Service para contar cuantas telas activas hay
	public int countTelasActivas();
	
	//Metodo para contar cuantos forros activos hay
	public int countForrosActivos();

	public List<Object[]> findMaterialByTipo(Long idTipoMaterial,Long idMaterial); 
	public Integer disponibles(Long id);
}


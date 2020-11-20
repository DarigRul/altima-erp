package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ComprasRequisicionAlmacen;
import com.altima.springboot.app.models.entity.ComprasRequisicionAlmacenMaterial;

public interface IComprasRequisicionAlmacenService {

	
	Object[] infoUsuario(Long id);
	
	List<Object []> AllMateriales();
	
	void save(ComprasRequisicionAlmacen obj);
	
	void save(ComprasRequisicionAlmacenMaterial obj);
	
	List<Object []> view(Long id);
	
	ComprasRequisicionAlmacen findOne(Long id);
	
	List<Object []> viewMaterial(Long id);
	
	void deleteRequisicionMaterial(Long idRequision);
	
	ComprasRequisicionAlmacenMaterial findOne(String idMateriales, String tipo,String cantidad, Long idRequisicion);
}

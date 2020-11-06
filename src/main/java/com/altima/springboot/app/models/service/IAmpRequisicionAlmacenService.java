package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.AmpRequisicionAlmacen;
import com.altima.springboot.app.models.entity.AmpRequisicionAlmacenMaterial;

public interface IAmpRequisicionAlmacenService {

	
	Object[] infoUsuario(Long id);
	
	List<Object []> AllMateriales();
	
	void save(AmpRequisicionAlmacen obj);
	
	void save(AmpRequisicionAlmacenMaterial obj);
	
	List<Object []> view(Long id);
	
	AmpRequisicionAlmacen findOne(Long id);
	
	List<Object []> viewMaterial(Long id);
	
	void deleteRequisicionMaterial(Long idRequision);
	
	AmpRequisicionAlmacenMaterial findOne(String idMateriales, String tipo,String cantidad, Long idRequisicion);
	
	List<Object []> viewListEmpleado();
}

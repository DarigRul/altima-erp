package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.AmpInventarioProovedor;
import com.altima.springboot.app.models.entity.AmpInventarioProovedorPrecio;

public interface IAmpInventarioProovedorService {
	
	List<AmpInventarioProovedor> findAll(Long iduser);

	void save(AmpInventarioProovedor obj);

	void delete(Long id);

	AmpInventarioProovedor findOne(Long id);
	
	List<Object []> Proveedores();
	
	List<Object []> View(Long id, String tipo);
	
	void savePrecio(AmpInventarioProovedorPrecio obj);
	
	Float findOnePrecio(Long id);

	List<Object []> ViewPagos(Long id);

}

package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.AmpInventario;

public interface IAmpInventarioService {
	
	List<Object []> findAll();

	void save(AmpInventario iventario);

	void delete(Long id);

	AmpInventario findOne(Long id);
	
	Integer SumClas(Long id);


}

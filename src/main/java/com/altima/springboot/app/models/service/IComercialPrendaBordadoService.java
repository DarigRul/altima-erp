package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialPrendaBordado;

public interface IComercialPrendaBordadoService {
	
	List<ComercialPrendaBordado> findAll(Long id);

	void save(ComercialPrendaBordado ComercialCliente);

	void delete(Long id);

	ComercialPrendaBordado findOne(Long id);
	
	List<Object []> findAllCoordinado(Long id);
	
	public Float sumBordados (Long id);
	
	public Float precio_coor_prenda (Long id);

}

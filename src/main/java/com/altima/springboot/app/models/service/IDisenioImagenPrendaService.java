package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.DisenioPrendaImagen;

public interface IDisenioImagenPrendaService 
{
	List<DisenioPrendaImagen> findAll();

	void save(DisenioPrendaImagen disenioImagenprenda);

	void delete(Long id);

	DisenioPrendaImagen findOne(Long id);
	
	List<DisenioPrendaImagen> findByPrenda(Long id);
}

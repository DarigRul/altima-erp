package com.altima.springboot.app.models.service;

import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialmagenInventario;
import com.altima.springboot.app.models.entity.DisenioLookup;


public interface IComercialImagenInventarioService {
	
	ComercialmagenInventario findOne(Long Id);
	
	
	void save(ComercialmagenInventario imagenInventario);
	
	Object findImagen(Long idprenda, Long idTela);
	
	public  ComercialmagenInventario findByidPrendaAndidTela(Long idprenda, Long idTela);
	
	

}

package com.altima.springboot.app.models.service;

import java.util.List;


import com.altima.springboot.app.models.entity.DisenioPrendaImagen;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;



public interface IInventarioService {
	
	
	public List<ProduccionDetallePedido> listInventario();
	
	void save(DisenioPrendaImagen diseñoPrendaImagen);
	
	Object findOnePreImg(Long id);
	
	
	DisenioPrendaImagen findOne(Long Id);
	
	
	public String Exist ( Long id);
	
	public List<ProduccionDetallePedido> listCatalogoInventario();
	

}

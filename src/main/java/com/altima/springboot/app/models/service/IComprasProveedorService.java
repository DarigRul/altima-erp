package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComprasProveedores;

public interface IComprasProveedorService {

	ComprasProveedores findOne(Long Id);
	
	List<ComprasProveedores> findAll ();
	
	void save (ComprasProveedores comprasProveedores);
}

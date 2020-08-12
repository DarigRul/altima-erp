package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComprasProveedorContacto;

public interface IComprasProveedorContactoService {

	void save (ComprasProveedorContacto comprasProveedorContacto);
	
	List<ComprasProveedorContacto> findAllByProveedor (Long id);
	
	ComprasProveedorContacto findOne (Long id);
	
	void deleteInexistentes (List<ComprasProveedorContacto> comprasProveedorContacto);
	
	
}

package com.altima.springboot.app.models.service;

import com.altima.springboot.app.models.entity.ComprasProveedorCredito;

public interface IComprasProveedorCreditoService {

	void save (ComprasProveedorCredito comprasProveedorCredito);
	
	ComprasProveedorCredito findByProveedor (Long id);
}

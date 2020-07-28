package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialClienteSucursal;

public interface IComercialClienteSucursalService {
	
	List<ComercialClienteSucursal> findAll();
	
	List<ComercialClienteSucursal> ClienteSucursales(Long id,Long idagente);

	void save(ComercialClienteSucursal sucursal);

	void delete(Long id);

	ComercialClienteSucursal findOne(Long id);
	
	
	List<ComercialClienteSucursal> findListaSucrusales();

	List<ComercialClienteSucursal> findListaSucrusalesCliente(long id);

}

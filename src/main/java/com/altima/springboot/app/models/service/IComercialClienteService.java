package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteFactura;

public interface IComercialClienteService {
	
	List<ComercialCliente> findAll(Long iduser);

	void save(ComercialCliente ComercialCliente);

	void delete(Long id);

	ComercialCliente findOne(Long id);
	public Integer Contador (String tipo);
	
	List<ComercialCliente> findAllEstatus1(Long iduser);
	
	//ComercialClienteFacturacionRepository
	List<ComercialClienteFactura> ListaFactura(Long id);
	void saveFactura(ComercialClienteFactura factura);
	ComercialClienteFactura findOneFactura(Long id);
	
	public Integer ContadorFacturas (Long id);

	List<Object[]> findAllAgentes();

}

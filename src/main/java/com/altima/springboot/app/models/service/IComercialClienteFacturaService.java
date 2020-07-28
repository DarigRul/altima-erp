package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialClienteFactura;



public interface IComercialClienteFacturaService {
	List<ComercialClienteFactura> findListaFacturaCliente(long id);

}

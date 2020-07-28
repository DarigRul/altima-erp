package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPrendaCliente;

public interface IDisenioPrendaClienteService 
{
	List<DisenioPrendaCliente> findAllByPrenda(Long id);

	void save(DisenioPrendaCliente disenioPrendaCliente);

	void delete(Long[] idClientes, Long idPrenda);
	
	
}

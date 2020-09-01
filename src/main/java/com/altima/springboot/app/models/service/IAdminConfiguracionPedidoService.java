package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;

public interface IAdminConfiguracionPedidoService {
	
	List<AdminConfiguracionPedido> findAll();
	List<Object []> findAllView();

	void save(AdminConfiguracionPedido config);

	void delete(Long id);

	AdminConfiguracionPedido findOne(Long id);
	
	List<Object []> pedidos();
	

}

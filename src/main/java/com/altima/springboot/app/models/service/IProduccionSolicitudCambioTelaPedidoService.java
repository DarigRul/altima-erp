package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionSolicitudCambioTelaPedido;

public interface IProduccionSolicitudCambioTelaPedidoService {

	List<ProduccionSolicitudCambioTelaPedido> findAll();

	void save(ProduccionSolicitudCambioTelaPedido obj);

	void delete(Long id);

	ProduccionSolicitudCambioTelaPedido findOne(Long id);
	
	List<Object[]> pedidosCerrados ();
	
	List <Object []> infPedido (Long id);
	
	List <Object []> View ();
}

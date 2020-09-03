package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;

public interface ICargaPedidoService {
	
	List<ComercialPedidoInformacion> findAll();

	void save(ComercialPedidoInformacion pedido);

	void delete(Long id);

	ComercialPedidoInformacion findOne(Long id);
	
	List<Object []> CargaPedidoVista(Long iduser);
	
	List<Object []> PedidosExistenteIdEmpresa(Long id);
	
	List<String> ValidarCantidadEspecial(Long id);

}

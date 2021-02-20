package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.MaquilaControlPedido;

public interface IMaquilaControlPedidoService {

	List<MaquilaControlPedido> findAllMaquilaControlPedido();

	List<ComercialCliente> findAllCliente();

	List<Object[]> findAllPrendaModelo();

	void save(MaquilaControlPedido maquilacontrol);

	MaquilaControlPedido findOne(Long id);

}

package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.MaquilaControlPedidoBulto;

public interface IMaquilaControlPedidoBultoService {

	List<MaquilaControlPedidoBulto> Listar(Long id);

	void save(MaquilaControlPedidoBulto maquilacontrolpedidobulto);

	String ContarOperaciones(String idprenda);

	void delete(Long id);

	List<Object[]> GenerarTickets(String idcontrol, String idprenda);

}

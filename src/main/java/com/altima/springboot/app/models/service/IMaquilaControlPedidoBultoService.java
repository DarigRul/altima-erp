package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.MaquilaControlPedidoBulto;

public interface IMaquilaControlPedidoBultoService {

	List<MaquilaControlPedidoBulto> Listar(Long id);

	void save(MaquilaControlPedidoBulto maquilacontrolpedidobulto);

}

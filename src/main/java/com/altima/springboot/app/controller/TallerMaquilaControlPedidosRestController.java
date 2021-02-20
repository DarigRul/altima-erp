package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.MaquilaControlPedido;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoService;

@RestController
public class TallerMaquilaControlPedidosRestController {

@Autowired
IMaquilaControlPedidoService maquilaControlPedidoService;
	
	@GetMapping("/listar-control-pedidos-one")
	public MaquilaControlPedido ListOne(Long id){
		
	return maquilaControlPedidoService.findOne(id);
		
	}
	
}

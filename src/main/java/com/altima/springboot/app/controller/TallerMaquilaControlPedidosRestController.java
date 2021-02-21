package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.MaquilaControlPedido;
import com.altima.springboot.app.models.entity.MaquilaControlPedidoBulto;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoBultoService;
import com.altima.springboot.app.models.service.IMaquilaControlPedidoService;

@RestController
public class TallerMaquilaControlPedidosRestController {

@Autowired
IMaquilaControlPedidoService maquilaControlPedidoService;
@Autowired
IMaquilaControlPedidoBultoService maquilaControlPedidoBultoService;
	
	@GetMapping("/listar-control-pedidos-one")
	public MaquilaControlPedido ListOne(Long id){
		
	return maquilaControlPedidoService.findOne(id);
		
	}
	
	@GetMapping("/listar-control-pedidos-bulto")
	public List<MaquilaControlPedidoBulto> ListControlPedidosBulto(Long id){
		
	return maquilaControlPedidoBultoService.Listar(id);
		
	}
	
	@PostMapping("/guardar-control-pedidos-bulto")
	public boolean guardarcontrolpedidosbulto(Float cantidadprenda,Long id) {
		boolean response=false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			MaquilaControlPedidoBulto maquilacontrolpedidobulto= new MaquilaControlPedidoBulto();
			maquilacontrolpedidobulto.setCantidadPrendaBulto(cantidadprenda);
			maquilacontrolpedidobulto.setIdControlPedido(id);
			maquilacontrolpedidobulto.setCreadoPor(auth.getName());
			maquilacontrolpedidobulto.setActualizadoPor(auth.getName());
			maquilacontrolpedidobulto.setEstatus("1");
			maquilacontrolpedidobulto.setFechaCreacion(hourdateFormat.format(date));
			maquilacontrolpedidobulto.setUltimaFechaModificacion(hourdateFormat.format(date));
			maquilaControlPedidoBultoService.save(maquilacontrolpedidobulto);
response=true;
		} catch (Exception e) {
		response=false;
		}
		
		return response;
	}
	
	
}

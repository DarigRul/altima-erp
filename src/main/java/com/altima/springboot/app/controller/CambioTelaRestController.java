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

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ProduccionSolicitudCambioTelaPedido;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IProduccionSolicitudCambioTelaPedidoService;

@RestController
public class CambioTelaRestController {
	
	@Autowired
	private IProduccionSolicitudCambioTelaPedidoService CambioTelaService;
	
	@Autowired
	private ICargaPedidoService CargaPedidoService;
	
	@GetMapping("/listar-pedidos-cerrados")
    public List<Object []> getComercialLookupByTipo(){
        return CambioTelaService.pedidosCerrados();
    }
    
    @GetMapping("/info-pedido")
    public List<Object []> buscarPedido (Long id) {
    	return CambioTelaService.infPedido(id);
    }
    
    @PostMapping("/guardar-solicitud-tela")
    public boolean buscarPedido (Long idPedido, String motivo) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ProduccionSolicitudCambioTelaPedido cambio = new ProduccionSolicitudCambioTelaPedido();
		cambio.setId_pedido(idPedido);
		cambio.setIdText("Cambio");
		cambio.setMotivo(motivo);
		cambio.setCreadoPor(auth.getName());
		cambio.setFechaCreacion(hourdateFormat.format(date));
    	cambio.setEstatus("1");
    	cambio.setEstatusEnvio("0");
    	CambioTelaService.save(cambio);
    	
    	cambio.setIdText("CAMTE"+(cambio.getIdTelaPedido()+1000));
    	return false;
    }

}

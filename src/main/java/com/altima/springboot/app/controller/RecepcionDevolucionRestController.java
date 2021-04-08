package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucion;
import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucionHistorico;
import com.altima.springboot.app.models.service.IServicioClienteRecepcionDevolucionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecepcionDevolucionRestController {

    @Autowired
    private IServicioClienteRecepcionDevolucionService devolucionService;

    @RequestMapping(value = "/listar_pedidos_by_maquilero", method = RequestMethod.GET)
	public List<Object[]> pedidos(@RequestParam(name = "idMaquilero") Long idMaquilero) {
		return devolucionService.pedido(idMaquilero);
	}

    @RequestMapping(value = "/listar_op_by_maquilero_and_pedido", method = RequestMethod.GET)
	public List<Object[]> op(@RequestParam(name = "idMaquilero") Long idMaquilero,@RequestParam(name = "idPedido") Long idPedido ) {
		return devolucionService.op(idPedido, idMaquilero);
	}

    @RequestMapping(value = "/guardar_recepcion_devolucion", method = RequestMethod.GET)
	public boolean save(String idOp, String idMaquilero, String recibida, String pendiente ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ServicioClienteRecepcionDevolucion obj = new ServicioClienteRecepcionDevolucion();
        obj.setIdMaquilero(idMaquilero);
        obj.setIdOp(idOp);
        obj.setRecibida(recibida);
        devolucionService.save(obj);
        System.out.println("----->"+obj.getIdRecepcionDevolucion());
        ServicioClienteRecepcionDevolucionHistorico his = new ServicioClienteRecepcionDevolucionHistorico ();
        his.setIdRecepcionDevolucion(obj.getIdRecepcionDevolucion());
        his.setIdHistorico(obj.getIdRecepcionDevolucion());
        his.setCantidad(recibida);
        his.setMovimiento("Recepción");
        his.setFecha(hourdateFormat.format(date));
        devolucionService.savehistorico(his);
        return false;
	
	}

    
    
}

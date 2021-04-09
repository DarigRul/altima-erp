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

    @RequestMapping(value = "/detalles_recepcion_devolucion", method = RequestMethod.GET)
	public List<Object[]> detalles(@RequestParam(name = "idMaquilero") Long idMaquilero,@RequestParam(name = "num_movimiento") Long num_movimiento ) {
		return devolucionService.view(idMaquilero, num_movimiento);
	}

    @RequestMapping(value = "/detalles_historico_recepcion_devolucion", method = RequestMethod.GET)
	public List<Object[]> historico(@RequestParam(name = "idMaquilero") Long idMaquilero,@RequestParam(name = "num_movimiento") Long num_movimiento ) {
		return devolucionService.viewHistorico(idMaquilero, num_movimiento);
	}

    @RequestMapping(value = "/numero_movimiento_recepcion", method = RequestMethod.GET)
	public String nmu() {
		return devolucionService.num_movimiento();
	}

    //
    @RequestMapping(value = "/guardar_recibir_by_id", method = RequestMethod.GET)
	public List<Object[]>  recibir (Long id, String pendiente,String recibido, String cantidad) {

         Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ServicioClienteRecepcionDevolucion obj = devolucionService.findOne(id);
        obj.setPendiente(pendiente);
        obj.setRecibida(recibido);
        devolucionService.save(obj);
        ServicioClienteRecepcionDevolucionHistorico his = new ServicioClienteRecepcionDevolucionHistorico ();
        his.setIdRecepcionDevolucion(id);
        his.setCantidad(cantidad);
        his.setMovimiento("Recepción");
        his.setFecha(hourdateFormat.format(date));
        devolucionService.savehistorico(his);
		return devolucionService.view(Long.parseLong(obj.getIdMaquilero()), Long.parseLong(obj.getNumMovimiento()));
	}

    @RequestMapping(value = "/guardar_devolver_by_id", method = RequestMethod.GET)
	public List<Object[]>  deolver (Long id, String recibido,String cantidad, String dev) {
        Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ServicioClienteRecepcionDevolucion obj = devolucionService.findOne(id);
        obj.setRecibida(recibido);
        obj.setDev(dev);
        devolucionService.save(obj);
        ServicioClienteRecepcionDevolucionHistorico his = new ServicioClienteRecepcionDevolucionHistorico ();
        his.setIdRecepcionDevolucion(id);
        his.setCantidad(cantidad);
        his.setMovimiento("Devolución");
        his.setFecha(hourdateFormat.format(date));
        devolucionService.savehistorico(his);
		return devolucionService.view(Long.parseLong(obj.getIdMaquilero()), Long.parseLong(obj.getNumMovimiento()));
	}
    //
    @RequestMapping(value = "/guardar_recibir_devolucion_by_id", method = RequestMethod.GET)
	public List<Object[]>  recibir_devolucion (Long id, String recibido,String cantidad, String dev) {
        Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ServicioClienteRecepcionDevolucion obj = devolucionService.findOne(id);
        obj.setRecibida(recibido);
        obj.setDev(dev);
        devolucionService.save(obj);
        ServicioClienteRecepcionDevolucionHistorico his = new ServicioClienteRecepcionDevolucionHistorico ();
        his.setIdRecepcionDevolucion(id);
        his.setCantidad(cantidad);
        his.setMovimiento("Recepción de devolución");
        his.setFecha(hourdateFormat.format(date));
        devolucionService.savehistorico(his);
		return devolucionService.view(Long.parseLong(obj.getIdMaquilero()), Long.parseLong(obj.getNumMovimiento()));
	}

    @RequestMapping(value = "/guardar_recepcion_devolucion", method = RequestMethod.GET)
	public List<Object[]> save(String idOp, String idMaquilero, String recibida, String pendiente, String num) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ServicioClienteRecepcionDevolucion obj = new ServicioClienteRecepcionDevolucion();
        obj.setIdMaquilero(idMaquilero);
        obj.setIdOp(idOp);
        obj.setRecibida(recibida);
        obj.setNumMovimiento(num);
        obj.setPendiente(pendiente);
        obj.setDev("0");
        devolucionService.save(obj);
        ServicioClienteRecepcionDevolucionHistorico his = new ServicioClienteRecepcionDevolucionHistorico ();
        his.setIdRecepcionDevolucion(obj.getIdRecepcionDevolucion());
      
        his.setCantidad(recibida);
        his.setMovimiento("Recepción");
        his.setFecha(hourdateFormat.format(date));
        devolucionService.savehistorico(his);
        return devolucionService.view(Long.parseLong(idMaquilero), Long.parseLong(num));
	
	}

    
    
}

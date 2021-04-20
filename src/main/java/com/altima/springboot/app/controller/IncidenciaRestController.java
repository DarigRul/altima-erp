package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionIncidencia;
import com.altima.springboot.app.models.service.IProduccionIncidenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidenciaRestController {

    @Autowired
    private IProduccionIncidenciaService incidenciaService;
    
	@RequestMapping(value = "/listar_pedidos_by_maquilero_incidencia", method = RequestMethod.GET)
	public List<Object[]> list (@RequestParam("idMaquilero") Long idMaquilero) {
        return incidenciaService.pedidos(idMaquilero);
	}

    @RequestMapping(value = "/listar_op_incidencia", method = RequestMethod.GET)
	public List<Object[]> listOp (@RequestParam("idPedido") Long idPedido) {
        return incidenciaService.OP(idPedido);
	}

    @RequestMapping(value = "/guardar_incidencia_produccion", method = RequestMethod.GET)
	public boolean save (String maquilero,String pedido,String op, String cantidad,String descuento,String monto, String reporte) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        ProduccionIncidencia inci = new ProduccionIncidencia();
        inci.setIdMaquileroolio(maquilero);
        inci.setIdPedido(pedido);
        inci.setIdOp(op);
        inci.setCantidad(cantidad);
        inci.setDescuento(descuento);
        inci.setCantidadDescuento(monto);
        inci.setReporte(reporte);
        inci.setEstatus(1);
        inci.setCreadoPor(auth.getName());
        inci.setFechaCreacion(hourdateFormat.format(date));
        incidenciaService.save(inci);
        inci.setFolio("INCI"+(inci.getIdIncidencia()+1000));
        incidenciaService.save(inci);
        return true;

	}
    //
    @RequestMapping(value = "/editar_incidencia_produccion", method = RequestMethod.GET)
	public boolean edit (@RequestParam("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        ProduccionIncidencia inci = incidenciaService.findOne(id);
        inci.setEstatus(2);
        inci.setActualizadoPor(auth.getName());
        inci.setUltimaFechaModificacion(hourdateFormat.format(date));
        incidenciaService.save(inci);
    
        return true;

	}
}

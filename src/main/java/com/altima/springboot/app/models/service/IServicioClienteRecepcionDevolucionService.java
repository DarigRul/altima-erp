package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucionHistorico;
import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucion;

public interface IServicioClienteRecepcionDevolucionService {

    List<Object []> maquileros ();

    List<Object []> pedido (Long idMaquilero);

    List<Object []> op (Long idPedido, Long idMaquilero);

    void save (ServicioClienteRecepcionDevolucion obj );

    ServicioClienteRecepcionDevolucion findOne(Long id);

    void savehistorico (ServicioClienteRecepcionDevolucionHistorico obj );

    List<Object []> viewPricipal ();

    List<Object []> view (Long idMaquilero, Long num_movimiento);

    List<Object []> viewHistorico (Long idMaquilero, Long num_movimiento);

    String num_movimiento ();
    
}

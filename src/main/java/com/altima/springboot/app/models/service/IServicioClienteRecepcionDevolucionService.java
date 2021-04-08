package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucionHistorico;
import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucion;

public interface IServicioClienteRecepcionDevolucionService {

    List<Object []> maquileros ();

    List<Object []> pedido (Long idMaquilero);

    List<Object []> op (Long idPedido, Long idMaquilero);

    void save (ServicioClienteRecepcionDevolucion obj );

    ServicioClienteRecepcionDevolucion findOneFechaCoorPrenda(Integer id);

    void savehistorico (ServicioClienteRecepcionDevolucionHistorico obj );

    List<Object []> viewPricipal ();
    
}

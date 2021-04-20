package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionIncidencia;

public interface IProduccionIncidenciaService {

    List<Object []> maquileros ();
    List<Object []> pedidos(Long idMaquilero);
    List<Object []> OP(Long idPedido);
    void save (ProduccionIncidencia obj);
    ProduccionIncidencia findOne (Long id);
    List<Object []> view();
    
}

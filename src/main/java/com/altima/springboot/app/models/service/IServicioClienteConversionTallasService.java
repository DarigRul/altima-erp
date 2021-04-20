package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteConversionTallas;

public interface IServicioClienteConversionTallasService {

    List<Object[]> listarPedidosEstatus3();
    
    List<Object []> view();

    List<Object []> devolverDatos(Long id);

    List<Object []> devolverEditar(Long id);

    void save(ServicioClienteConversionTallas objecto);

    ServicioClienteConversionTallas findOne(Long id);

    
}

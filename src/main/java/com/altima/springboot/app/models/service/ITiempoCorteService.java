package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionFechaCoordinadoPrenda;
public interface ITiempoCorteService {

    List<Object []> view ();
    
    List<Object []> detallesFolio (String folio);

    void save (ProduccionFechaCoordinadoPrenda obj );

    ProduccionFechaCoordinadoPrenda findOneFechaCoorPrenda(Integer id);

    Integer validarExistenciaHorasHombre  (String fecha);

    Integer validarHorasHabiles (String fecha, String folio);

    String recuperarIdPorFecha (String fecha);

    List<Object []> detallesCalendario (String fecha1, String fecha2);

    String buscarFechaPorFolio(String folio);
    
}

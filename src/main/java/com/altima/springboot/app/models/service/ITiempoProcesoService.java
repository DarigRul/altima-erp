package com.altima.springboot.app.models.service;


import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionFechaCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ProduccionFechaExplosionProceso;
public interface ITiempoProcesoService {

    List<Object []> view (Long idProceso,String programa);
    void save (ProduccionFechaExplosionProceso obj );

    ProduccionFechaExplosionProceso findOneFechaCoorPrenda(Integer id);
    
    List<Object []> viewDetalles (Long idProceso, String secuencia);

    Integer validarHorasHabiles (String fecha, String secuencia, Integer idProceso);
}

package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ServicioClienteObservacionProceso;

public interface IServicioClienteObservacionProcesoService {
    
    List<Object []> view(Long iduser);
    List<Object[]> fullUser();

    ServicioClienteObservacionProceso findOne(Long id);

    boolean validarDuplicadoIdIsNull(Long idUser,Long idProceso , String observacion);

    boolean validarDuplicadoIdIsNotNul(Long idUser,Long idProceso , String observacion, Long idObservacion);
    
    void save(ServicioClienteObservacionProceso obj);
    
}

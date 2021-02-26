package com.altima.springboot.app.models.service;

import java.util.List;

public interface IEmpalmeTelasService {

    List<Object []> view ();
    
    List<Object []> detallesTelas (Long idCoorPrenda);

    List<Object[]> listarProcesosDisponiblesAdmin();

    List<Object[]> listarProcesosDisponiblesUser(Long idUser);
    
    List<Object []> listarByProceso(Long idProceso);
    
}

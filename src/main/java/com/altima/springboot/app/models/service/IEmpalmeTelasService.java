package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ProgramarTelasListDto;

public interface IEmpalmeTelasService {

    List<ProgramarTelasListDto> view (String pedido);
    
    List<Object []> detallesTelas (Long idCoorPrenda);

    List<Object[]> listarProcesosDisponiblesAdmin();

    List<Object[]> listarProcesosDisponiblesUser(Long idUser);
    
    List<Object []> listarByProceso(Long idProceso,String programa);
    
}

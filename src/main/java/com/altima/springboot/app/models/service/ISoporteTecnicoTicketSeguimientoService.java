package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicketSeguimiento;


public interface ISoporteTecnicoTicketSeguimientoService {
    
    List<SoporteTecnicoTicketSeguimiento> findAll();

    void save(SoporteTecnicoTicketSeguimiento ticketSeguimiento);

    void delete(Long id);

    SoporteTecnicoTicketSeguimiento findOne(Long id);

    List<SoporteTecnicoTicketDto> findSeguimientoByIdTicket(Long idTicket);
}

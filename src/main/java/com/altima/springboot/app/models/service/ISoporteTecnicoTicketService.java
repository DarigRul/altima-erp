package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicket;

public interface ISoporteTecnicoTicketService {
    
    List<SoporteTecnicoTicket> findAll();

    void save(SoporteTecnicoTicket ticket);

    void delete(Long id);

    SoporteTecnicoTicket findOne(Long id);

    List<SoporteTecnicoTicketDto> findAllTicket();

    SoporteTecnicoTicketDto findTicket(Long id);
}

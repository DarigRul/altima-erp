package com.altima.springboot.app.repository;


import java.util.List;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteTecnicoTicketRepository extends JpaRepository<SoporteTecnicoTicket,Long>{
    
    @Query(name = "ticket",nativeQuery = true)
    List<SoporteTecnicoTicketDto> findAllTicket(Long idTicket);

    @Query(name = "ticket",nativeQuery = true)
    SoporteTecnicoTicketDto findTicket(Long idTicket);
}

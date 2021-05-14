package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicketSeguimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteTecnicoTicketSeguimientoRepository extends JpaRepository<SoporteTecnicoTicketSeguimiento,Long>{
    
    @Query(name = "seguimiento",nativeQuery = true)
    List<SoporteTecnicoTicketDto> findSeguimientoByIdTicket(Long idTicket);
}

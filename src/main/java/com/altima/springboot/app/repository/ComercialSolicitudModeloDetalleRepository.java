package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ComercialSolicitudModeloDetalle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercialSolicitudModeloDetalleRepository extends CrudRepository<ComercialSolicitudModeloDetalle,Long>{
    
}

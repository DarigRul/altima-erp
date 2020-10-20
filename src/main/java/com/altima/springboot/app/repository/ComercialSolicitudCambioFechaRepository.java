package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ComercialSolicitudCambioFecha;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercialSolicitudCambioFechaRepository extends CrudRepository<ComercialSolicitudCambioFecha,Long>{
    
}

package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ComercialSolicitudModelo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercialSolicitudModeloRepository extends CrudRepository<ComercialSolicitudModelo,Long>{
    
}

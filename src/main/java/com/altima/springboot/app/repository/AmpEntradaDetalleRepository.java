package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpEntradaDetalle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpEntradaDetalleRepository extends CrudRepository<AmpEntradaDetalle,Long>{
    
}

package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpTraspasoDetalleRepository extends CrudRepository<AmpTraspasoDetalle,Long>{
    
}

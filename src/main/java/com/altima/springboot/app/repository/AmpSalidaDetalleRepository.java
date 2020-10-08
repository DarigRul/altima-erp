package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpSalidaDetalle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpSalidaDetalleRepository extends CrudRepository<AmpSalidaDetalle,Long> {
    
}

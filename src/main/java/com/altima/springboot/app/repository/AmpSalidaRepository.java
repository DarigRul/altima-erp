package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpSalida;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpSalidaRepository extends CrudRepository<AmpSalida,Long>{
    
}

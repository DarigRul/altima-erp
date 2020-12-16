package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpTelaFaltante;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpTelaFaltanteRepository extends CrudRepository<AmpTelaFaltante,Long>{
    
}

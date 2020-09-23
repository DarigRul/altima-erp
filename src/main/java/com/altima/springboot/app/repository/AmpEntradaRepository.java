package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpEntrada;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpEntradaRepository extends CrudRepository<AmpEntrada,Long>{
    
}

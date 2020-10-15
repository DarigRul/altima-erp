package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpTraspaso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpTraspasoRepository extends CrudRepository<AmpTraspaso,Long>{
    
}

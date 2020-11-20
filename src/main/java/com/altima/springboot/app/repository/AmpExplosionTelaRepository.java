package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.AmpExplosionTela;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpExplosionTelaRepository extends CrudRepository<AmpExplosionTela,Long>{
    
}

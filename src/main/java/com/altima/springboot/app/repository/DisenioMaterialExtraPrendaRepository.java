package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.DisenioMaterialExtraPrenda;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisenioMaterialExtraPrendaRepository extends CrudRepository<DisenioMaterialExtraPrenda,Long>{
    
}
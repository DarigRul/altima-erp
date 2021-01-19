package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ComprasOrden;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprasOrdenRepository extends CrudRepository<ComprasOrden,Long>{
    
}

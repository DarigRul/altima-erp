package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.MaquilaPrendaOperacion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaquilaPrendaOperacionRepository extends CrudRepository<MaquilaPrendaOperacion, Long>  {
    
}

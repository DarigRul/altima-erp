package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.DisenioLookup;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisenioLookupRepository extends CrudRepository<DisenioLookup, Long> {
    
}

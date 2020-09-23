package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialLookup;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComercialLookupRepository extends CrudRepository<ComercialLookup,Long>{
    
    public List<ComercialLookup> findByTipoLookup(String tipoLookup);
}

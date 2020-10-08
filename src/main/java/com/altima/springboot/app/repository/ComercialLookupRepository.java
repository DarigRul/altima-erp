package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialLookup;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComercialLookupRepository extends CrudRepository<ComercialLookup,Long>{
    
    public List<ComercialLookup> findByTipoLookup(String tipoLookup);

    @Query("select u from ComercialLookup u where u.tipoLookup=?1 and u.Estatus='1'")
    public List<ComercialLookup> findByTipoLookupAndEstatus(String tipoLookup);
}

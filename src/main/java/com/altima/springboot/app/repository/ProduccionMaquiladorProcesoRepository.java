package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ProduccionMaquiladorProceso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionMaquiladorProcesoRepository extends CrudRepository<ProduccionMaquiladorProceso,Long>{
    
    Long deleteByIdProcesoAndIdMaquilador(Long idProceso,Long idMaquilador);
}

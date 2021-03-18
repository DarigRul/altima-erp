package com.altima.springboot.app.models.service;

import com.altima.springboot.app.models.entity.ProduccionMaquiladorPrendas;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionMaquiladorPrendasRepository extends CrudRepository<ProduccionMaquiladorPrendas,Long>{
    
    Long deleteByIdMaquiladorAndIdFamiliaPrenda(Long idMaquilador,Long idFamiliaPrenda);
}

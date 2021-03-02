package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ProduccionTelaCalidad;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionTelaCalidadRepository extends CrudRepository<ProduccionTelaCalidad,Long>{
    
    ProduccionTelaCalidad findByIdTela(Long idTela);
}

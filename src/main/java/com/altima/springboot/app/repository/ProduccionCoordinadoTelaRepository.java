package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.ProduccionCoordinadoTela;

@Repository
public interface ProduccionCoordinadoTelaRepository extends CrudRepository<ProduccionCoordinadoTela, Long> {

}
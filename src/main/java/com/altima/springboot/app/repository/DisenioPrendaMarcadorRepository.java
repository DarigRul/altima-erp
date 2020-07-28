package com.altima.springboot.app.repository;


import com.altima.springboot.app.models.entity.DisenioPrendaMarcador;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisenioPrendaMarcadorRepository extends CrudRepository<DisenioPrendaMarcador, Long> {
   
}
package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.DisenioPrendaImagen;

@Repository
public interface DiseñoPrendaImgenRepository extends CrudRepository<DisenioPrendaImagen, Long> {

}


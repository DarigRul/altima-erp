package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.altima.springboot.app.models.entity.AmpAlmacen;

@Repository
public interface AmpAlmacenRepository extends CrudRepository<AmpAlmacen, Long> {

}
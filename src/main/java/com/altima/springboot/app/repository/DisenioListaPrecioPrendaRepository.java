package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.DisenioListaPrecioPrenda;

@Repository
public interface DisenioListaPrecioPrendaRepository extends CrudRepository<DisenioListaPrecioPrenda, Long> {
    DisenioListaPrecioPrenda findByidPrenda(Long idPrenda);
}

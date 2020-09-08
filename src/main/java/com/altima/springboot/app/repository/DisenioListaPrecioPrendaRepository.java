package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioListaPrecioPrenda;

@Repository
public interface DisenioListaPrecioPrendaRepository extends CrudRepository<DisenioListaPrecioPrenda, Long> {
    DisenioListaPrecioPrenda findByidPrenda(Long idPrenda);

    @Query(
  value = "Call alt_pr_carga_masiva_precios(?1,?2)", 
  nativeQuery = true)
    List<Object[]> prMasivo(Long id1,Long id2);
}

package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.DisenioCalidad;

@Repository
public interface DisenioCalidadRepository extends CrudRepository<DisenioCalidad, Long> {
    @Query("SELECT dc FROM DisenioCalidad dc WHERE dc.idMaterial = :id and dc.tipoMaterial = :tipo")
    DisenioCalidad findOneByParams(@Param("id") Long id, @Param("tipo") String tipo);
}

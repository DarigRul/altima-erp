package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.DisenioPrenda;

@Repository
public interface DisenioPrendaRepository extends CrudRepository<DisenioPrenda, Long> {

    @Query(value = "SELECT adl.nombre_lookup FROM alt_disenio_prenda adp INNER JOIN alt_disenio_lookup adl ON adl.id_lookup=adp.id_genero WHERE adp.id_prenda=:idPrenda",
    nativeQuery = true)
    String findGeneroByIdPrenda(Long idPrenda);

}

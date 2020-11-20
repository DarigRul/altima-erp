package com.altima.springboot.app.repository;

import java.util.Optional;

import com.altima.springboot.app.models.entity.AmpAlmacenUbicacionArticulo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpAlmacenUbicacionArticuloRepository extends CrudRepository<AmpAlmacenUbicacionArticulo,Long>{
 
    Optional<AmpAlmacenUbicacionArticulo> findByIdArticuloAndTipo(Long idArticulo,String tipo);
}

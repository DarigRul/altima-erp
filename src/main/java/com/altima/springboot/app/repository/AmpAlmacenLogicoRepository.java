package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import com.altima.springboot.app.models.entity.AmpAlmacenLogico;

public interface AmpAlmacenLogicoRepository extends CrudRepository<AmpAlmacenLogico, Long> {

    Optional<AmpAlmacenLogico> findByTipoAndNombreAlmacenLogico(String tipo,String nombreAlmacenLogico);

}

package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.altima.springboot.app.models.entity.AmpAlmacenLogico;

public interface AmpAlmacenLogicoRepository extends CrudRepository<AmpAlmacenLogico, Long> {

    AmpAlmacenLogico findByTipoAndNombreAlmacenLogico(String tipo,String nombreAlmacenLogico);

}

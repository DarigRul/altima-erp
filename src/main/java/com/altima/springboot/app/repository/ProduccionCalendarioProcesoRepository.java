package com.altima.springboot.app.repository;

import java.util.Optional;

import com.altima.springboot.app.models.entity.ProduccionCalendarioProceso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionCalendarioProcesoRepository extends CrudRepository<ProduccionCalendarioProceso,Long>{

    Optional<ProduccionCalendarioProceso> findByIdCalendarioFechaAndIdProceso(Long idCalendarioFecha,Long idProceso);
    
}

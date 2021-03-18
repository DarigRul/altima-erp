package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionCalendarioProceso;

public interface IProduccionCalendarioProcesoService {

    void save(ProduccionCalendarioProceso calendarioProceso);

    void delete(Long id);

    ProduccionCalendarioProceso findOne(Long id);

    ProduccionCalendarioProceso findByIdCalendarioFechaAndIdProceso(Long idCalendarioFecha,Long idProceso);

    List<ProduccionCalendarioProceso> findAll(Long id);
}

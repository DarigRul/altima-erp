package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ModificacionDto;
import com.altima.springboot.app.models.entity.ComercialTallaModificacion;

public interface IComercialTallaModificacionService {

    List<ComercialTallaModificacion> findAll(Long id);

    List<ModificacionDto> findModificacionesByidConcentradoTalla(Long idConcentradoTalla);

    void save(ComercialTallaModificacion modificacion);

    void delete(Long id);

    ComercialTallaModificacion findOne(Long id);
}

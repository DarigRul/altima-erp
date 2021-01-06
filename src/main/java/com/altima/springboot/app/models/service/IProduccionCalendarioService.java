package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionCalendario;
public interface IProduccionCalendarioService {

    Integer validarAnio(String anio);

    void crearCalendario(String fecha_incial, String fecha_final, String creado_por, String fecha_creacion);

    List<Object[]> mostrar_calendario(String fechaInicio , String fehaFin);

    ProduccionCalendario findOne (Long id);

    void save(ProduccionCalendario calendario);

    String restarHoras(String fechaInicio , String fehaFin);
    
}

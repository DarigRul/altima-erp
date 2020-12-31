package com.altima.springboot.app.models.service;

public interface IProduccionCalendarioService {

    Integer validarAnio(String anio);

    void crearCalendario(String fecha_incial, String fecha_final);
    
}

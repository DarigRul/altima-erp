package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UbicacionListDTO {
    @Id
    private Long idUbicacion;
    private String nombre;

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

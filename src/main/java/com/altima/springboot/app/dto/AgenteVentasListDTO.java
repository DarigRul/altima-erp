package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AgenteVentasListDTO {

    @Id
    private Long idAgenteVentas;
    private String nombreAgenteVentas;

    public Long getIdAgenteVentas() {
        return idAgenteVentas;
    }

    public void setIdAgenteVentas(Long idAgenteVentas) {
        this.idAgenteVentas = idAgenteVentas;
    }

    public String getNombreAgenteVentas() {
        return nombreAgenteVentas;
    }

    public void setNombreAgenteVentas(String nombreAgenteVentas) {
        this.nombreAgenteVentas = nombreAgenteVentas;
    }

    
    
}

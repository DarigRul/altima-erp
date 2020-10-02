package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComercialSolicitudModeloDetalleDTO {
    @Id
	private Long idSolicitudModeloDetalle;
	private Long idSolicitudModelo;
    private String idModelo;
    private String telefono;

    public Long getIdSolicitudModeloDetalle() {
        return idSolicitudModeloDetalle;
    }

    public void setIdSolicitudModeloDetalle(Long idSolicitudModeloDetalle) {
        this.idSolicitudModeloDetalle = idSolicitudModeloDetalle;
    }

    public Long getIdSolicitudModelo() {
        return idSolicitudModelo;
    }

    public void setIdSolicitudModelo(Long idSolicitudModelo) {
        this.idSolicitudModelo = idSolicitudModelo;
    }

    public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}

package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComercialSolicitudCambioFechaDTO {
    
    @Id
	private Long idSolicitudCambioFecha;
	private String idText;
    private String agente;
    private String fechaCreacion;
    private String cliente;
    private String folioPedido;
    private String fechaNueva;
    private String estatus;
    private String fechaCierre;

    public Long getIdSolicitudCambioFecha() {
        return idSolicitudCambioFecha;
    }

    public void setIdSolicitudCambioFecha(Long idSolicitudCambioFecha) {
        this.idSolicitudCambioFecha = idSolicitudCambioFecha;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(String folioPedido) {
        this.folioPedido = folioPedido;
    }

    public String getFechaNueva() {
        return fechaNueva;
    }

    public void setFechaNueva(String fechaNueva) {
        this.fechaNueva = fechaNueva;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}

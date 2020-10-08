package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComercialSolicitudModeloDTO {
    @Id
    private Long idSolicitudModelo;
    private String idText;
    private String fechaCreacion;
	private String nombreAgente;
	private String nombreCliente;
	private String fechaCita;
	private String horaSalidaAltima;
    private String estatus;

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraSalidaAltima() {
        return horaSalidaAltima;
    }

    public void setHoraSalidaAltima(String horaSalidaAltima) {
        this.horaSalidaAltima = horaSalidaAltima;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdSolicitudModelo() {
        return idSolicitudModelo;
    }

    public void setIdSolicitudModelo(Long idSolicitudModelo) {
        this.idSolicitudModelo = idSolicitudModelo;
    }



    
    
}

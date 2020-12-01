package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ListUbicacionRolloDto {
    
    @Id
    private String idText;
    private String casillero;
    private String lote;
    private String almacenFisico;
    private float cantidad;
    private String nombreTela;

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getCasillero() {
        return casillero;
    }

    public void setCasillero(String casillero) {
        this.casillero = casillero;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getAlmacenFisico() {
        return almacenFisico;
    }

    public void setAlmacenFisico(String almacenFisico) {
        this.almacenFisico = almacenFisico;
    }

    public String getNombreTela() {
        return nombreTela;
    }

    public void setNombreTela(String nombreTela) {
        this.nombreTela = nombreTela;
    }

    
}

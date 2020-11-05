package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntradasSalidasDTO implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    private String idText;
    private String fechaDocumento;
    @Id
    private String movimiento;
    private String concepto;
    private String nombreAlmacenLogico;
    private String referencias;

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNombreAlmacenLogico() {
        return nombreAlmacenLogico;
    }

    public void setNombreAlmacenLogico(String nombreAlmacenLogico) {
        this.nombreAlmacenLogico = nombreAlmacenLogico;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    
    
}

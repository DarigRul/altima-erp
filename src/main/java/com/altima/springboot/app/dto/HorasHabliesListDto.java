package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HorasHabliesListDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Id
    private String idCalendarioFecha;
    private String fecha;
    private float horasHombre;
    private float horasFavor;
    private float horasContra;
    private float horasProgramadas;

    public String getIdCalendarioFecha() {
        return idCalendarioFecha;
    }
    public void setIdCalendarioFecha(String idCalendarioFecha) {
        this.idCalendarioFecha = idCalendarioFecha;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public float getHorasProgramadas() {
        return horasProgramadas;
    }
    public void setHorasProgramadas(float horasProgramadas) {
        this.horasProgramadas = horasProgramadas;
    }
    public float getHorasHombre() {
        return horasHombre;
    }
    public void setHorasHombre(float horasHombre) {
        this.horasHombre = horasHombre;
    }
    public float getHorasFavor() {
        return horasFavor;
    }
    public void setHorasFavor(float horasFavor) {
        this.horasFavor = horasFavor;
    }
    public float getHorasContra() {
        return horasContra;
    }
    public void setHorasContra(float horasContra) {
        this.horasContra = horasContra;
    } 

    
}

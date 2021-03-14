package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "alt_produccion_calendario_proceso")
public class ProduccionCalendarioProceso implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
	@Column(name="id_calendario_proceso")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private Long idCalendarioProceso;
    
    @Column(name="id_calendario_fecha")
    private Long idCalendarioFecha;

    @Column(name="id_proceso")
    private Long idProceso;


    @Column(name="horas_hombre")
    private float horasHombre;

    @Column(name="horas_favor")
    private float horasFavor;

    @Column(name="horas_contra")
    private float horasContra;

    @Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

    public Long getIdCalendarioProceso() {
        return idCalendarioProceso;
    }

    public void setIdCalendarioProceso(Long idCalendarioProceso) {
        this.idCalendarioProceso = idCalendarioProceso;
    }

    public Long getIdCalendarioFecha() {
        return idCalendarioFecha;
    }

    public void setIdCalendarioFecha(Long idCalendarioFecha) {
        this.idCalendarioFecha = idCalendarioFecha;
    }

    public Long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
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

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getActualizadoPor() {
        return actualizadoPor;
    }

    public void setActualizadoPor(String actualizadoPor) {
        this.actualizadoPor = actualizadoPor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUltimaFechaModificacion() {
        return ultimaFechaModificacion;
    }

    public void setUltimaFechaModificacion(String ultimaFechaModificacion) {
        this.ultimaFechaModificacion = ultimaFechaModificacion;
    }
}

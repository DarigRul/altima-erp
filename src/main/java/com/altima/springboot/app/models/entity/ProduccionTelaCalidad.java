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
@Table(name = "alt_produccion_tela_calidad")
public class ProduccionTelaCalidad implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_tela_calidad")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long idTelaCalidad;

    @Column(name = "bloque_largo")
    private float bloqueLargo;

    @Column(name = "bloque_ancho")
    private float bloqueAncho;

    @Column(name = "plancha_largo")
    private float planchaLargo;

    @Column(name = "plancha_ancho")
    private float planchaAncho;

    @Column(name = "id_tela")
    private Long idTela;

    @Column(name = "id_entretela")
    private Long idEntretela;

    @Column(name = "creado_por")
    private String creadoPor;

    @Column(name = "actualizado_por")
    private String actualizadoPor;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ultima_fecha_modificacion")
    private String ultimaFechaModificacion;

    public Long getIdTelaCalidad() {
        return idTelaCalidad;
    }

    public void setIdTelaCalidad(Long idTelaCalidad) {
        this.idTelaCalidad = idTelaCalidad;
    }

    public float getBloqueLargo() {
        return bloqueLargo;
    }

    public void setBloqueLargo(float bloqueLargo) {
        this.bloqueLargo = bloqueLargo;
    }

    public float getBloqueAncho() {
        return bloqueAncho;
    }

    public void setBloqueAncho(float bloqueAncho) {
        this.bloqueAncho = bloqueAncho;
    }

    public float getPlanchaLargo() {
        return planchaLargo;
    }

    public void setPlanchaLargo(float planchaLargo) {
        this.planchaLargo = planchaLargo;
    }

    public float getPlanchaAncho() {
        return planchaAncho;
    }

    public void setPlanchaAncho(float planchaAncho) {
        this.planchaAncho = planchaAncho;
    }

    public Long getIdTela() {
        return idTela;
    }

    public void setIdTela(Long idTela) {
        this.idTela = idTela;
    }

    public Long getIdEntretela() {
        return idEntretela;
    }

    public void setIdEntretela(Long idEntretela) {
        this.idEntretela = idEntretela;
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

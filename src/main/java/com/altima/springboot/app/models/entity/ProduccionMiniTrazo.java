package com.altima.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alt_produccion_mini_trazo")
public class ProduccionMiniTrazo implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "id_mini_trazo")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long idMiniTrazo;

	@Column(name = "id_prenda")
	private Long idPrenda;

	@Column(name = "ruta")
	private String ruta;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "fecha_creacion")
	private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ultima_fecha_modificacion")
	private Date ultimaFechaModificacion;

    public Long getIdMiniTrazo() {
        return idMiniTrazo;
    }

    public void setIdMiniTrazo(Long idMiniTrazo) {
        this.idMiniTrazo = idMiniTrazo;
    }

    public Long getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(Long idPrenda) {
        this.idPrenda = idPrenda;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaFechaModificacion() {
        return ultimaFechaModificacion;
    }

    public void setUltimaFechaModificacion(Date ultimaFechaModificacion) {
        this.ultimaFechaModificacion = ultimaFechaModificacion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "ProduccionMiniTrazo [actualizadoPor=" + actualizadoPor + ", creadoPor=" + creadoPor + ", descripcion="
                + descripcion + ", fechaCreacion=" + fechaCreacion + ", idMiniTrazo=" + idMiniTrazo + ", idPrenda="
                + idPrenda + ", ruta=" + ruta + ", ultimaFechaModificacion=" + ultimaFechaModificacion + "]";
    }
    
}

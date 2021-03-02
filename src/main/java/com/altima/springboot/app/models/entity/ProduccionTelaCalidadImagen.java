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
@Table(name = "alt_produccion_tela_calidad_imagen")
public class ProduccionTelaCalidadImagen implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "id_tela_calidad_imagen")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long idTelaCalidadImagen;

	@Column(name = "id_tela")
	private Long idTela;

	@Column(name = "ruta")
	private String ruta;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd-MM-yyyy'T'HH:mm:ss.SSSZ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "fecha_creacion")
	private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd-MM-yyyy'T'HH:mm:ss.SSSZ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ultima_fecha_modificacion")
	private Date ultimaFechaModificacion;


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

    public Long getIdTelaCalidadImagen() {
        return idTelaCalidadImagen;
    }

    public void setIdTelaCalidadImagen(Long idTelaCalidadImagen) {
        this.idTelaCalidadImagen = idTelaCalidadImagen;
    }

    public Long getIdTela() {
        return idTela;
    }

    public void setIdTela(Long idTela) {
        this.idTela = idTela;
    }

    
}

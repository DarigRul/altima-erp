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
@Table(name = "alt_comercial_bordado_parte_bordado")
public class ComercialBordadoParteBordado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_bordado_parte_bordado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idBordadoParteBordado;
	
	@Column(name="id_bordado")
	private Long idBordado;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="partes_bordado")
	private String partesBordado;
	
	@Column(name="numero_hilo")
	private String numeroHilo;
	
	@Column(name="color")
	private String color;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus")
	private String estatus;

	public Long getIdBordadoParteBordado() {
		return idBordadoParteBordado;
	}

	public void setIdBordadoParteBordado(Long idBordadoParteBordado) {
		this.idBordadoParteBordado = idBordadoParteBordado;
	}

	public Long getIdBordado() {
		return idBordado;
	}

	public void setIdBordado(Long idBordado) {
		this.idBordado = idBordado;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getPartesBordado() {
		return partesBordado;
	}

	public void setPartesBordado(String partesBordado) {
		this.partesBordado = partesBordado;
	}

	public String getNumeroHilo() {
		return numeroHilo;
	}

	public void setNumeroHilo(String numeroHilo) {
		this.numeroHilo = numeroHilo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

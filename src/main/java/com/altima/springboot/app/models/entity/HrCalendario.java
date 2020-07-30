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
@Table(name = "alt_hr_calendario")
public class HrCalendario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_calendario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "native", strategy = "native")
	private Long idCalendario;

	@Column(name = "fecha")
	private String fecha;

	@Column(name = "nombre_calendario")
	private String nombreCalendario;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

	@Column(name = "fecha_creacion")
	private String fechaCreacion;

	@Column(name = "ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	@Column(name = "estatus")
	private String Estatus;

	public Long getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombreCalendario() {
		return nombreCalendario;
	}

	public void setNombreCalendario(String nombreCalendario) {
		this.nombreCalendario = nombreCalendario;
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

	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

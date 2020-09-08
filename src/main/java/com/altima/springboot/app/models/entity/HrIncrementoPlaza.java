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
@Table(name = "alt_hr_incremento_plaza")
public class HrIncrementoPlaza implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_incremento_plaza")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long idIncrementoPlaza;

	@Column(name = "id_puesto")
	private Long idPuesto;

	@Column(name = "id_text")
	private String idText;

	@Column(name = "numero_plaza")
	private String numeroPlaza;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

	@Column(name = "fecha_creacion")
	private String fechaCreacion;

	@Column(name = "ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	@Column(name = "sueldo")
	private String sueldo;

	@Column(name = "fecha_solicitud")
	private String fechaSolicitud;

	@Column(name = "fecha_autorizacion")
	private String fechaAutorizacion;

	@Column(name = "motivo_rechazo")
	private String motivoRechazo;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "estatus")
	private String estatus;

	@Column(name = "estatus_plaza")
	private String estatusPlaza;

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public Long getIdIncrementoPlaza() {
		return idIncrementoPlaza;
	}

	public void setIdIncrementoPlaza(Long idIncrementoPlaza) {
		this.idIncrementoPlaza = idIncrementoPlaza;
	}

	public Long getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(Long idPuesto) {
		this.idPuesto = idPuesto;
	}

	public String getNumeroPlaza() {
		return numeroPlaza;
	}

	public void setNumeroPlaza(String numeroPlaza) {
		this.numeroPlaza = numeroPlaza;
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

	public String getSueldo() {
		return sueldo;
	}

	public void setSueldo(String sueldo) {
		this.sueldo = sueldo;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(String fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusPlaza() {
		return estatusPlaza;
	}

	public void setEstatusPlaza(String estatusPlaza) {
		this.estatusPlaza = estatusPlaza;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

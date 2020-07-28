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
@Table(name = "alt_disenio_composicion_icuidado")
public class DisenioComposicionIcuidado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_composicion_icuidado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idUmedidaIcuidado;
	
	@Column(name="id_composicion")
	private Long idComposicion;
	
	@Column(name="id_instruccion_cuidado")
	private Long idInstruccionesCuidado;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	

	public Long getIdUmedidaIcuidado() {
		return idUmedidaIcuidado;
	}

	public void setIdUmedidaIcuidado(Long idUmedidaIcuidado) {
		this.idUmedidaIcuidado = idUmedidaIcuidado;
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

	public Long getIdComposicion() {
		return idComposicion;
	}

	public void setIdComposicion(Long idComposicion) {
		this.idComposicion = idComposicion;
	}

	public Long getIdInstruccionesCuidado() {
		return idInstruccionesCuidado;
	}

	public void setIdInstruccionesCuidado(Long idInstruccionesCuidado) {
		this.idInstruccionesCuidado = idInstruccionesCuidado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}

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
@Table(name = "alt_disenio_familia_composicion_tela")
public class DisenioFamiliaComposicionTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_familia_composicion_tela")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idFamiliaComposicionTela;
	
	@Column(name="id_tela")
	private Long idTela;
	
	@Column(name="id_composicion")
	private Long idComposicion;
	
	@Column(name="porcentaje_composicion")
	private String porcentajeComposicion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	

	public Long getIdFamiliaComposicion() {
		return idComposicion;
	}

	public void setIdFamiliaComposicion(Long idFamiliaComposicion) {
		this.idComposicion = idFamiliaComposicion;
	}

	public Long getIdFamiliaComposicionTela() {
		return idFamiliaComposicionTela;
	}

	public void setIdFamiliaComposicionTela(Long idFamiliaComposicionTela) {
		this.idFamiliaComposicionTela = idFamiliaComposicionTela;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
	}

	public String getComposicion() {
		return porcentajeComposicion;
	}

	public void setComposicion(String composicion) {
		this.porcentajeComposicion = composicion;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
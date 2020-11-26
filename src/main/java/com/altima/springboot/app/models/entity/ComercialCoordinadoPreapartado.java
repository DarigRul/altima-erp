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
@Table(name="alt_comercial_coordinado_preapartado")
public class ComercialCoordinadoPreapartado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	@Id
	@Column(name="id_coordinado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdCoordinado;
	
	@Column(name="id_text")
	private String IdText;
	
	@Column(name="total_prendas")
	private int totalPrendas;
	
	@Column(name="id_preapartado")
	private Long idPreapartado;
	
	public Long getIdCoordinado() {
		return IdCoordinado;
	}

	public void setIdCoordinado(Long idCoordinado) {
		IdCoordinado = idCoordinado;
	}

	public String getIdText() {
		return IdText;
	}

	public void setIdText(String idText) {
		IdText = idText;
	}

	public int getTotalPrendas() {
		return totalPrendas;
	}

	public void setTotalPrendas(int totalPrendas) {
		this.totalPrendas = totalPrendas;
	}

	public Long getIdPreapartado() {
		return idPreapartado;
	}

	public void setIdPreapartado(Long idPreapartado) {
		this.idPreapartado = idPreapartado;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;



}
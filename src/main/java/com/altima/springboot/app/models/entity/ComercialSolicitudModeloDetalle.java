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
@Table(name = "alt_comercial_solicitud_modelo_detalle")
public class ComercialSolicitudModeloDetalle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_solicitud_modelo_detalle")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idSolicitudModeloDetalle;
	
	@Column(name="id_solicitud_modelo")
	private Long idSolicitudModelo;
	
	@Column(name="id_modelo")
	private Long idModelo;

	public Long getIdSolicitudModeloDetalle() {
		return idSolicitudModeloDetalle;
	}

	public void setIdSolicitudModeloDetalle(Long idSolicitudModeloDetalle) {
		this.idSolicitudModeloDetalle = idSolicitudModeloDetalle;
	}

	public Long getIdSolicitudModelo() {
		return idSolicitudModelo;
	}

	public void setIdSolicitudModelo(Long idSolicitudModelo) {
		this.idSolicitudModelo = idSolicitudModelo;
	}

	public Long getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}

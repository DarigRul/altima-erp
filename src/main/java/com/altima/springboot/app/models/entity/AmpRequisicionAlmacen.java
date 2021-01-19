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
@Table(name = "alt_amp_requisicion_almacen")
public class AmpRequisicionAlmacen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_requisicion_almacen")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idRequsicionAlmacen;
	
	@Column(name="id_solicitante")
	private Long idSolicitante;

	@Column(name="id_text")
	private String idText;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus_envio")
	private String estatusEnvio;

	@Column(name="tipo_requisicion")
	private String tipoRequisicion;

	public Long getIdRequsicionAlmacen() {
		return idRequsicionAlmacen;
	}

	public void setIdRequsicionAlmacen(Long idRequsicionAlmacen) {
		this.idRequsicionAlmacen = idRequsicionAlmacen;
	}

	public Long getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Long idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
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

	public String getEstatusEnvio() {
		return estatusEnvio;
	}

	public void setEstatusEnvio(String estatusEnvio) {
		this.estatusEnvio = estatusEnvio;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getTipoRequisicion() {
		return tipoRequisicion;
	}

	public void setTipoRequisicion(String tipoRequisicion) {
		this.tipoRequisicion = tipoRequisicion;
	}

}

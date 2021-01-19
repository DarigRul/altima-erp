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
@Table(name = "alt_comercial_solicitud_servicio_al_cliente_corrida")
public class ComercialSolicitudServicioAlClienteCorrida implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_solicitud_servicio_al_cliente_corrida")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idSolicitudServicioAlClienteCorrida;
	
	@Column(name="id_solicitud_servicio_al_cliente")
	private Long idSolicitudServicioAlCliente;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="genero")
	private String genero;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus")
	private String estatus;

	@Column(name="cantidad")
	private Integer cantidad;

	
	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdSolicitudServicioAlClienteCorrida() {
		return idSolicitudServicioAlClienteCorrida;
	}

	public void setIdSolicitudServicioAlClienteCorrida(Long idSolicitudServicioAlClienteCorrida) {
		this.idSolicitudServicioAlClienteCorrida = idSolicitudServicioAlClienteCorrida;
	}

	public Long getIdSolicitudServicioAlCliente() {
		return idSolicitudServicioAlCliente;
	}

	public void setIdSolicitudServicioAlCliente(Long idSolicitudServicioAlCliente) {
		this.idSolicitudServicioAlCliente = idSolicitudServicioAlCliente;
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

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	

}

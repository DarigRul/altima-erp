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
@Table(name = "alt_comercial_ticket_estatus")
public class ComercialTicketEstatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_ticket_estatus")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idTicketEstatus;
	
	@Column(name = "id_ticket")
	private Long idTicket;
	
	@Column(name="estatus_nombre")
	private String estatusNombre;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

	@Column(name = "fecha_creacion")
	private String fechaCreacion;

	@Column(name = "ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="id_empleado")
	private Integer idEmpleado;

	public Long getIdTicketEstatus() {
		return idTicketEstatus;
	}

	public void setIdTicketEstatus(Long idTicketEstatus) {
		this.idTicketEstatus = idTicketEstatus;
	}

	public Long getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}

	public String getEstatusNombre() {
		return estatusNombre;
	}

	public void setEstatusNombre(String estatusNombre) {
		this.estatusNombre = estatusNombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}

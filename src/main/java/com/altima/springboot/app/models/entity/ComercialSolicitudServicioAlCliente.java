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
@Table(name = "alt_comercial_solicitud_servicio_al_cliente")
public class ComercialSolicitudServicioAlCliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="id_solicitud_servicio_al_cliente")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idSolicitudServicioAlCliente;
	
	@Column(name="id_pedido_informacion")
	private Long idPedidoInformacion;
	
	@Column(name="id_cliente")
	private Long idCliente;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="fecha_hora_de_cita")
	private String fechaHoraDeCita;
	
	@Column(name="hora_salida_altima")
	private String horaSalidaAltima;
	
	@Column(name="actividad")
	private String actividad;

	@Column(name="caballeros_por_atender")
	private Long caballerosPorAtender;
	
	@Column(name="damas_por_atender")
	private Long damasPorAtender;
	
	@Column(name="comentarios")
	private String comentarios;
	
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

	@Column(name="id_sucursal")
	private String idSucrsal;

	@Column(name="dirigirse_con")
	private String dirigirseCon;
	
	
	

	public String getHoraSalidaAltima() {
		return horaSalidaAltima;
	}

	public void setHoraSalidaAltima(String horaSalidaAltima) {
		this.horaSalidaAltima = horaSalidaAltima;
	}

	public Long getIdSolicitudServicioAlCliente() {
		return idSolicitudServicioAlCliente;
	}

	public void setIdSolicitudServicioAlCliente(Long idSolicitudServicioAlCliente) {
		this.idSolicitudServicioAlCliente = idSolicitudServicioAlCliente;
	}

	public Long getIdPedidoInformacion() {
		return idPedidoInformacion;
	}

	public void setIdPedidoInformacion(Long idPedidoInformacion) {
		this.idPedidoInformacion = idPedidoInformacion;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}


	public String getFechaHoraDeCita() {
		return fechaHoraDeCita;
	}

	public void setFechaHoraDeCita(String fechaHoraDeCita) {
		this.fechaHoraDeCita = fechaHoraDeCita;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public Long getCaballerosPorAtender() {
		return caballerosPorAtender;
	}

	public void setCaballerosPorAtender(Long caballerosPorAtender) {
		this.caballerosPorAtender = caballerosPorAtender;
	}

	public Long getDamasPorAtender() {
		return damasPorAtender;
	}

	public void setDamasPorAtender(Long damasPorAtender) {
		this.damasPorAtender = damasPorAtender;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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

	public String getIdSucrsal() {
		return idSucrsal;
	}

	public void setIdSucrsal(String idSucrsal) {
		this.idSucrsal = idSucrsal;
	}

	public String getDirigirseCon() {
		return dirigirseCon;
	}

	public void setDirigirseCon(String dirigirseCon) {
		this.dirigirseCon = dirigirseCon;
	}
	
	
	
	
}

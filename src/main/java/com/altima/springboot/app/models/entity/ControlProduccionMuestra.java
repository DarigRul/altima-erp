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
@Table(name = "alt_control_produccion_muestra")
public class ControlProduccionMuestra implements Serializable {

	/**
	 * 
	 * 
	 * 
	 * 
	 * jjjj
	 * 
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_control_produccion_muestra")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idControlProduccionMuestra;
	
	@Column(name="id_pedido")
	private Long idPedido;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="fecha_recepcion")
	private String fechaRecepcion;
	
	@Column(name="fecha_Entrega")
	private String fechaEntrega;
	
	@Column(name="estatus_tiempo")
	private String estatusTiempo;
	
	@Column(name="id_operario")
	private String idOperario;
	
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
	
	

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public Long getIdControlProduccionMuestra() {
		return idControlProduccionMuestra;
	}

	public void setIdControlProduccionMuestra(Long idControlProduccionMuestra) {
		this.idControlProduccionMuestra = idControlProduccionMuestra;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getEstatusTiempo() {
		return estatusTiempo;
	}

	public void setEstatusTiempo(String estatusTiempo) {
		this.estatusTiempo = estatusTiempo;
	}

	public String getIdOperario() {
		return idOperario;
	}

	public void setIdOperario(String idOperario) {
		this.idOperario = idOperario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

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
@Table(name="alt_comercial_preapartado")
public class ComercialPreApartado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	@Id
	@Column(name="id_preapartado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdPreapartado;
	
	@Column(name="id_text")
	private String IdText;
	
	@Column(name="id_cliente")
	private Long idCliente;
	
	@Column(name="num_personas")
	private int numPersonas;
	
	@Column(name="id_empleado")
	private Long idEmpleado;
	
	@Column(name="referencia_pedido")
	private String referenciaPedido;
	
	@Column(name="fecha_preapartado")
	private String fechaPreapartado;
	
	@Column(name="fecha_vigencia")
	private String fechaVigencia;
	
	@Column(name="estatus")
	private int estatus;
	
	@Column(name="estatus_pedido")
	private int estatusPedido;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;

	public Long getIdPreapartado() {
		return IdPreapartado;
	}

	public void setIdPreapartado(Long idPreapartado) {
		IdPreapartado = idPreapartado;
	}

	public String getIdText() {
		return IdText;
	}

	public void setIdText(String idText) {
		IdText = idText;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getReferenciaPedido() {
		return referenciaPedido;
	}

	public void setReferenciaPedido(String referenciaPedido) {
		this.referenciaPedido = referenciaPedido;
	}

	public String getFechaPreapartado() {
		return fechaPreapartado;
	}

	public void setFechaPreapartado(String fechaPreapartado) {
		this.fechaPreapartado = fechaPreapartado;
	}

	public String getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public int getEstatusPedido() {
		return estatusPedido;
	}

	public void setEstatusPedido(int estatusPedido) {
		this.estatusPedido = estatusPedido;
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

}

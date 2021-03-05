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
@Table(name = "alt_maquila_control_pedidos")
public class MaquilaControlPedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_control_pedido")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idControlPedido;

	@Column(name="pedido")
	private String pedido;
	
	@Column(name="cliente")
	private String cliente;
	
	@Column(name="orden_produccion")
	private String ordenProduccion;
	
	@Column(name="coordinado")
	private String coordinado;
	
	@Column(name="prenda")
	private String prenda;
	
	@Column(name="modelo")
	private String modelo;
	
	@Column(name="clave_tela")
	private String claveTela;
	
	@Column(name="confeccion")
	private Integer confeccion;
	
	@Column(name="fecha_recepcion")
	private String fechaRecepcion;
	
	@Column(name="fecha_programada_entrega")
	private String fechaProgramadaEntrega;
	
	@Column(name="fecha_entrega")
	private String fechaEntrega;
	
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
	
	@Column(name="id_prenda")
	private Long idPrenda;

	public Long getIdControlPedido() {
		return idControlPedido;
	}

	public void setIdControlPedido(Long idControlPedido) {
		this.idControlPedido = idControlPedido;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getOrdenProduccion() {
		return ordenProduccion;
	}

	public void setOrdenProduccion(String ordenProduccion) {
		this.ordenProduccion = ordenProduccion;
	}

	public String getCoordinado() {
		return coordinado;
	}

	public void setCoordinado(String coordinado) {
		this.coordinado = coordinado;
	}

	public String getPrenda() {
		return prenda;
	}

	public void setPrenda(String prenda) {
		this.prenda = prenda;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getClaveTela() {
		return claveTela;
	}

	public void setClaveTela(String claveTela) {
		this.claveTela = claveTela;
	}

	public Integer getConfeccion() {
		return confeccion;
	}

	public void setConfeccion(Integer confeccion) {
		this.confeccion = confeccion;
	}

	public String getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(String fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public String getFechaProgramadaEntrega() {
		return fechaProgramadaEntrega;
	}

	public void setFechaProgramadaEntrega(String fechaProgramadaEntrega) {
		this.fechaProgramadaEntrega = fechaProgramadaEntrega;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}
	
	
	
	
}

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
@Table(name = "alt_maquila_control_pedidos_bulto")
public class MaquilaControlPedidoBulto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_control_pedido_embultado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idControlPedidoEmbultado;

	@Column(name="id_control_pedido")
	private Long idControlPedido;
	
	@Column(name="cantidad_prenda_bulto")
	private Float cantidadPrendaBulto;
	
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

	public Long getIdControlPedido() {
		return idControlPedido;
	}

	public void setIdControlPedido(Long idControlPedido) {
		this.idControlPedido = idControlPedido;
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

	public Long getIdControlPedidoEmbultado() {
		return idControlPedidoEmbultado;
	}

	public void setIdControlPedidoEmbultado(Long idControlPedidoEmbultado) {
		this.idControlPedidoEmbultado = idControlPedidoEmbultado;
	}

	public Float getCantidadPrendaBulto() {
		return cantidadPrendaBulto;
	}

	public void setCantidadPrendaBulto(Float cantidadPrendaBulto) {
		this.cantidadPrendaBulto = cantidadPrendaBulto;
	}
	
	
	
	
}

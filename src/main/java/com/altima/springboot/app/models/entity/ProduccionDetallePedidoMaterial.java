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
@Table(name = "alt_produccion_detalle_pedido_material")
public class ProduccionDetallePedidoMaterial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_detalle_pedido_material")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idDetallePedidoMaterial;
    
	@Column(name="id_pedido_detalle")
	private Long idPedidoDetalle;
	
	public Long getIdPedidoDetalle() {
		return idPedidoDetalle;
	}

	public void setIdPedidoDetalle(Long idPedidoDetalle) {
		this.idPedidoDetalle = idPedidoDetalle;
	}

	@Column(name="id_material")
	private Long idMaterial;
	
	@Column(name="color")
	private String color;
	
	@Column(name="color_codigo")
	private String colorCodigo;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	
	public Long getIdDetallePedidoMaterial() {
		return idDetallePedidoMaterial;
	}

	public void setIdDetallePedidoMaterial(Long idDetallePedidoMaterial) {
		this.idDetallePedidoMaterial = idDetallePedidoMaterial;
	}

	public Long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorCodigo() {
		return colorCodigo;
	}

	public void setColorCodigo(String colorCodigo) {
		this.colorCodigo = colorCodigo;
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
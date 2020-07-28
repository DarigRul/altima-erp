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
@Table(name = "alt_comercial_cotizacion_total")
public class ComercialCotizacionTotal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_total_cotizacion")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idTotalCotizacion;
	
	@Column(name="id_cotizacion")
	private Long idCotizacion;
	
	@Column(name="subtotal")
	private String subtotal;
	
	@Column(name="anticipo_porcentaje")
	private String anticipoPorcentaje;
	
	@Column(name="anticipo_monto")
	private String anticipoMonto;
	
	@Column(name="descuento_porcentaje")
	private String descuentoPorcentaje;
	
	@Column(name="descuento_monto")
	private String descuentoMonto;
	
	@Column(name="descuento_cargo")
	private String descuentoCargo;
	
	@Column(name="iva")
	private String iva;
	
	@Column(name="iva_monto")
	private String ivaMonto;
	
	@Column(name="total")
	private String total;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	public Long getIdTotalCotizacion() {
		return idTotalCotizacion;
	}

	public void setIdTotalCotizacion(Long idTotalCotizacion) {
		this.idTotalCotizacion = idTotalCotizacion;
	}

	public Long getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getAnticipoPorcentaje() {
		return anticipoPorcentaje;
	}

	public void setAnticipoPorcentaje(String anticipoPorcentaje) {
		this.anticipoPorcentaje = anticipoPorcentaje;
	}

	public String getAnticipoMonto() {
		return anticipoMonto;
	}

	public void setAnticipoMonto(String anticipoMonto) {
		this.anticipoMonto = anticipoMonto;
	}

	public String getDescuentoPorcentaje() {
		return descuentoPorcentaje;
	}

	public void setDescuentoPorcentaje(String descuentoPorcentaje) {
		this.descuentoPorcentaje = descuentoPorcentaje;
	}

	public String getDescuentoMonto() {
		return descuentoMonto;
	}

	public void setDescuentoMonto(String descuentoMonto) {
		this.descuentoMonto = descuentoMonto;
	}

	public String getDescuentoCargo() {
		return descuentoCargo;
	}

	public void setDescuentoCargo(String descuentoCargo) {
		this.descuentoCargo = descuentoCargo;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getIvaMonto() {
		return ivaMonto;
	}

	public void setIvaMonto(String ivaMonto) {
		this.ivaMonto = ivaMonto;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
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

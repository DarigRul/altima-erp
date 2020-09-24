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
@Table(name = "alt_amp_entrada_detalle")
public class AmpEntradaDetalle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_entrada_detalle")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idEntradaDetalle;
	
	@Column(name="id_entrada")
	private Long idEntrada;
	
	@Column(name="id_articulo")
	private Long idArticulo;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="cantidad")
	private Long cantidad;
	
	@Column(name="descuento_mon")
	private String descuentoMon;
	
	@Column(name="descuento_por")
	private String descuentoPor;
	
	@Column(name="observaciones")
	private String observaciones;

	public Long getIdEntradaDetalle() {
		return idEntradaDetalle;
	}

	public void setIdEntradaDetalle(Long idEntradaDetalle) {
		this.idEntradaDetalle = idEntradaDetalle;
	}

	public Long getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Long idEntrada) {
		this.idEntrada = idEntrada;
	}

	public Long getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescuentoMon() {
		return descuentoMon;
	}

	public void setDescuentoMon(String descuentoMon) {
		this.descuentoMon = descuentoMon;
	}

	public String getDescuentoPor() {
		return descuentoPor;
	}

	public void setDescuentoPor(String descuentoPor) {
		this.descuentoPor = descuentoPor;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}

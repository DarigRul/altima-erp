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
@Table(name = "alt_amp_traspaso_detalle")
public class AmpTraspasoDetalle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_traspaso_detalle")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idTraspasoDetalle;
	
	@Column(name="id_traspaso")
	private Long idTraspaso;
	
	@Column(name="id_articulo")
	private Long idArticulo;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="cantidad")
	private Float cantidad;
	
	@Column(name="descuento_mon")
	private String descuentoMon;
	
	@Column(name="descuento_por")
	private String descuentoPor;
	
	@Column(name="observaciones")
	private String observaciones;

	public Long getIdTraspasoDetalle() {
		return idTraspasoDetalle;
	}

	public void setIdTraspasoDetalle(Long idTraspasoDetalle) {
		this.idTraspasoDetalle = idTraspasoDetalle;
	}

	public Long getIdTraspaso() {
		return idTraspaso;
	}

	public void setIdTraspaso(Long idTraspaso) {
		this.idTraspaso = idTraspaso;
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

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
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

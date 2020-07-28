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
@Table(name = "alt_comercial_detalle_precio_prenda")
public class ComercialDetallePrecioPrenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_detalle_precio_prenda")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idListaPrecioPrenda;

	@Column(name="id_coordinado_prenda")
	private Long idCoordinadoPrenda;
	
	@Column(name="id_prenda_bordado")
	private Long idPrendaBordado;
	
	@Column(name="porcentaje_adicional")
	private String porcentajeAdicional;
	
	@Column(name="monto_adicional")
	private String montoAdicional;
	
	@Column(name="total_precio")
	private String totalPrecio;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="linea_express")
	private String lineaExpress;
	
	@Column(name="venta_interna")
	private String ventaInterna;
	
	
	
	
	
	public String getLineaExpress() {
		return lineaExpress;
	}

	public void setLineaExpress(String lineaExpress) {
		this.lineaExpress = lineaExpress;
	}

	public String getVentaInterna() {
		return ventaInterna;
	}

	public void setVentaInterna(String ventaInterna) {
		this.ventaInterna = ventaInterna;
	}

	public Long getIdListaPrecioPrenda() {
		return idListaPrecioPrenda;
	}

	public void setIdListaPrecioPrenda(Long idListaPrecioPrenda) {
		this.idListaPrecioPrenda = idListaPrecioPrenda;
	}

	public Long getIdCoordinadoPrenda() {
		return idCoordinadoPrenda;
	}

	public void setIdCoordinadoPrenda(Long idCoordinadoPrenda) {
		this.idCoordinadoPrenda = idCoordinadoPrenda;
	}

	public Long getIdPrendaBordado() {
		return idPrendaBordado;
	}

	public void setIdPrendaBordado(Long idPrendaBordado) {
		this.idPrendaBordado = idPrendaBordado;
	}

	public String getPorcentajeAdicional() {
		return porcentajeAdicional;
	}

	public void setPorcentajeAdicional(String porcentajeAdicional) {
		this.porcentajeAdicional = porcentajeAdicional;
	}

	public String getMontoAdicional() {
		return montoAdicional;
	}

	public void setMontoAdicional(String montoAdicional) {
		this.montoAdicional = montoAdicional;
	}

	public String getTotalPrecio() {
		return totalPrecio;
	}

	public void setTotalPrecio(String totalPrecio) {
		this.totalPrecio = totalPrecio;
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

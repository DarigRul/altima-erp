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
@Table(name = "alt_compras_proveedor_credito")
public class ComprasProveedorCredito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_proveedor_credito")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idProveedorCredito;
	
	@Column(name="id_proveedor")
	private Long IdProveedor;
	
	@Column(name="manejo_credito")
	private String manejoCredito;
	
	@Column(name="dias_credito")
	private String diasCredito;
	
	@Column(name="limite_credito")
	private String limiteCredito;
	
	@Column(name="saldo")
	private String saldo;
	
	@Column(name="forma_pago")
	private String formaPago;
	
	@Column(name="observaciones")
	private String observaciones;
	
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

	public Long getIdProveedorCredito() {
		return idProveedorCredito;
	}

	public void setIdProveedorCredito(Long idProveedorCredito) {
		this.idProveedorCredito = idProveedorCredito;
	}

	public Long getIdProveedor() {
		return IdProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		IdProveedor = idProveedor;
	}

	public String getManejoCredito() {
		return manejoCredito;
	}

	public void setManejoCredito(String manejoCredito) {
		this.manejoCredito = manejoCredito;
	}

	public String getDiasCredito() {
		return diasCredito;
	}

	public void setDiasCredito(String diasCredito) {
		this.diasCredito = diasCredito;
	}

	public String getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(String limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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
	
	
	
	

}

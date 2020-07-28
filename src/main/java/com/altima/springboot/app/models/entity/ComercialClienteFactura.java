package com.altima.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alt_comercial_cliente_factura")
public class ComercialClienteFactura implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_cliente_factura")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idClienteFacturaF;
	
	@Column(name="id_cliente")
	private Long idClienteF;
	
	@Column(name="id_direccion")
	private Long idDireccionF;
	
	@Column(name="id_text")
	private String idTextF;
	
	@Column(name="creado_por")
	private String creadoPorF;
	
	@Column(name="actualizado_por")
	private String actualizadoPorF;
	
	@Column(name="fecha_creacion")
	private String fechaCreacionF;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacionF;
	
	@Column(name="estatus")
	private String estatusF;
	
	@Column(name="cuenta_contable")
	private String cuentaContableF;
	
	@Column(name="rfc_factura")
	private String rfcFacturaF;
	
	@Column(name="razon_social")
	private String razon_socialF;
	
	@Column(name="observacion")
	private String observacion;
	
	

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getIdClienteFacturaF() {
		return idClienteFacturaF;
	}

	public void setIdClienteFacturaF(Long idClienteFacturaF) {
		this.idClienteFacturaF = idClienteFacturaF;
	}

	public Long getIdClienteF() {
		return idClienteF;
	}

	public void setIdClienteF(Long idClienteF) {
		this.idClienteF = idClienteF;
	}

	public Long getIdDireccionF() {
		return idDireccionF;
	}

	public void setIdDireccionF(Long idDireccionF) {
		this.idDireccionF = idDireccionF;
	}

	public String getIdTextF() {
		return idTextF;
	}

	public void setIdTextF(String idTextF) {
		this.idTextF = idTextF;
	}

	public String getCreadoPorF() {
		return creadoPorF;
	}

	public void setCreadoPorF(String creadoPorF) {
		this.creadoPorF = creadoPorF;
	}

	public String getActualizadoPorF() {
		return actualizadoPorF;
	}

	public void setActualizadoPorF(String actualizadoPorF) {
		this.actualizadoPorF = actualizadoPorF;
	}

	public String getFechaCreacionF() {
		return fechaCreacionF;
	}

	public void setFechaCreacionF(String fechaCreacionF) {
		this.fechaCreacionF = fechaCreacionF;
	}

	public String getUltimaFechaModificacionF() {
		return ultimaFechaModificacionF;
	}

	public void setUltimaFechaModificacionF(String ultimaFechaModificacionF) {
		this.ultimaFechaModificacionF = ultimaFechaModificacionF;
	}

	public String getEstatusF() {
		return estatusF;
	}

	public void setEstatusF(String estatusF) {
		this.estatusF = estatusF;
	}

	public String getCuentaContableF() {
		return cuentaContableF;
	}

	public void setCuentaContableF(String cuentaContableF) {
		this.cuentaContableF = cuentaContableF;
	}

	public String getRfcFacturaF() {
		return rfcFacturaF;
	}

	public void setRfcFacturaF(String rfcFacturaF) {
		this.rfcFacturaF = rfcFacturaF;
	}

	public String getRazon_socialF() {
		return razon_socialF;
	}

	public void setRazon_socialF(String razon_socialF) {
		this.razon_socialF = razon_socialF;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}

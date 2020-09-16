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
@Table(name = "alt_amp_salida")
public class AmpSalida implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_salida")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idSalida;
	
	@Column(name="id_almacen_logico")
	private Long idAlmacenLogico;
	
	@Column(name="id_concepto_salida")
	private Long idConceptoSalida;
	
	@Column(name="id_precio")
	private Long idPrecio;
	
	@Column(name="referencia")
	private String referencia;
	
	@Column(name="observaciones")
	private String observaciones;
	
	@Column(name="total_documento")
	private Long totalDocumento;
	
	@Column(name="entregado_por")
	private String entregadoPor;
	
	@Column(name="fecha_documento")
	private String fechaDocumento;
	
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

	public Long getIdSalida() {
		return idSalida;
	}

	public void setIdSalida(Long idSalida) {
		this.idSalida = idSalida;
	}

	public Long getIdAlmacenLogico() {
		return idAlmacenLogico;
	}

	public void setIdAlmacenLogico(Long idAlmacenLogico) {
		this.idAlmacenLogico = idAlmacenLogico;
	}

	public Long getIdConceptoSalida() {
		return idConceptoSalida;
	}

	public void setIdConceptoSalida(Long idConceptoSalida) {
		this.idConceptoSalida = idConceptoSalida;
	}

	public Long getIdPrecio() {
		return idPrecio;
	}

	public void setIdPrecio(Long idPrecio) {
		this.idPrecio = idPrecio;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getTotalDocumento() {
		return totalDocumento;
	}

	public void setTotalDocumento(Long totalDocumento) {
		this.totalDocumento = totalDocumento;
	}

	public String getEntregadoPor() {
		return entregadoPor;
	}

	public void setEntregadoPor(String entregadoPor) {
		this.entregadoPor = entregadoPor;
	}

	public String getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
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

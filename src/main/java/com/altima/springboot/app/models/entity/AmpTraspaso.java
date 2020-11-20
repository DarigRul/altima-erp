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
@Table(name = "alt_amp_traspaso")
public class AmpTraspaso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_traspaso")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idTraspaso;
	
	@Column(name="id_almacen_logico_origen")
	private Long idAlmacenLogicoOrigen;
	
	@Column(name="id_almacen_logico_destino")
	private Long idAlmacenLogicoDestino;
	
	@Column(name="id_concepto_entrada")
	private Long idConceptoEntrada;
	
	@Column(name="id_concepto_salida")
	private Long idConceptoSalida;
	
	@Column(name="total_documento")
	private Long totalDocumento;
	
	@Column(name="entregado_por")
	private String entregado_por;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="referencia")
	private String referencia;
	
	@Column(name="observaciones")
	private String observaciones;
	
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

	public Long getIdTraspaso() {
		return idTraspaso;
	}

	public void setIdTraspaso(Long idTraspaso) {
		this.idTraspaso = idTraspaso;
	}

	public Long getIdAlmacenLogicoOrigen() {
		return idAlmacenLogicoOrigen;
	}

	public void setIdAlmacenLogicoOrigen(Long idAlmacenLogicoOrigen) {
		this.idAlmacenLogicoOrigen = idAlmacenLogicoOrigen;
	}

	public Long getIdAlmacenLogicoDestino() {
		return idAlmacenLogicoDestino;
	}

	public void setIdAlmacenLogicoDestino(Long idAlmacenLogicoDestino) {
		this.idAlmacenLogicoDestino = idAlmacenLogicoDestino;
	}

	public Long getIdConceptoEntrada() {
		return idConceptoEntrada;
	}

	public void setIdConceptoEntrada(Long idConceptoEntrada) {
		this.idConceptoEntrada = idConceptoEntrada;
	}

	public Long getIdConceptoSalida() {
		return idConceptoSalida;
	}

	public void setIdConceptoSalida(Long idConceptoSalida) {
		this.idConceptoSalida = idConceptoSalida;
	}

	public Long getTotalDocumento() {
		return totalDocumento;
	}

	public void setTotalDocumento(Long totalDocumento) {
		this.totalDocumento = totalDocumento;
	}

	public String getEntregado_por() {
		return entregado_por;
	}

	public void setEntregado_por(String entregado_por) {
		this.entregado_por = entregado_por;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
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

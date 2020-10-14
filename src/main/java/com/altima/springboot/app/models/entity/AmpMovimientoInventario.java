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
@Table(name = "alt_amp_movimiento_inventario")
public class AmpMovimientoInventario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_movimiento")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idMovimiento;
	
	@Column(name="id_movimiento_detalle")
	private Long idMovimientoDetalle;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="tipo_movimiento")
	private String tipoMovimiento;
	
	@Column(name="fecha_documento")
	private String fechaDocumento;
	
	@Column(name="referencia")
	private String referencia;
	
	@Column(name="existencia_almacen")
	private Long existenciaAlmacen;
	
	@Column(name="existencia_general_inicial")
	private Long existenciaGeneralInicial;
	
	@Column(name="existencia_almacen_final")
	private Long existenciaAlmacenFinal;
	
	@Column(name="existencia_general_final")
	private Long existenciaGeneralFinal;
	
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

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public Long getIdMovimientoDetalle() {
		return idMovimientoDetalle;
	}

	public void setIdMovimientoDetalle(Long idMovimientoDetalle) {
		this.idMovimientoDetalle = idMovimientoDetalle;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Long getExistenciaAlmacen() {
		return existenciaAlmacen;
	}

	public void setExistenciaAlmacen(Long existenciaAlmacen) {
		this.existenciaAlmacen = existenciaAlmacen;
	}

	public Long getExistenciaGeneralInicial() {
		return existenciaGeneralInicial;
	}

	public void setExistenciaGeneralInicial(Long existenciaGeneralInicial) {
		this.existenciaGeneralInicial = existenciaGeneralInicial;
	}

	public Long getExistenciaAlmacenFinal() {
		return existenciaAlmacenFinal;
	}

	public void setExistenciaAlmacenFinal(Long existenciaAlmacenFinal) {
		this.existenciaAlmacenFinal = existenciaAlmacenFinal;
	}

	public Long getExistenciaGeneralFinal() {
		return existenciaGeneralFinal;
	}

	public void setExistenciaGeneralFinal(Long existenciaGeneralFinal) {
		this.existenciaGeneralFinal = existenciaGeneralFinal;
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

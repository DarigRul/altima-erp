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
@Table(name = "alt_amp_rollo_tela")
public class AmpRolloTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_rollo_tela")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idRolloTela;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="id_almacen_fisico")
	private Long idAlmacenFisico;

	@Column(name="id_almacen_logico")
	private Long idAlmacenLogico;
	
	@Column(name="id_tela")
	private Long idTela;

	@Column(name="id_pedido")
	private Long idPedido;
	
	@Column(name="cantidad")
	private Float cantidad;
	
	@Column(name="cantidad_original")
	private Float cantidadOriginal;
	
	@Column(name="lote")
	private String lote;
	
	public Float getCantidadOriginal() {
		return cantidadOriginal;
	}

	public void setCantidadOriginal(Float cantidadOriginal) {
		this.cantidadOriginal = cantidadOriginal;
	}

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
	
	

	public Long getIdAlmacenFisico() {
		return idAlmacenFisico;
	}

	public void setIdAlmacenFisico(Long idAlmacenFisico) {
		this.idAlmacenFisico = idAlmacenFisico;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
	}

	public Long getIdRolloTela() {
		return idRolloTela;
	}

	public void setIdRolloTela(Long idRolloTela) {
		this.idRolloTela = idRolloTela;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
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

	public Long getIdAlmacenLogico() {
		return idAlmacenLogico;
	}

	public void setIdAlmacenLogico(Long idAlmacenLogico) {
		this.idAlmacenLogico = idAlmacenLogico;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	
	
}

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
@Table(name = "alt_amp_explosion_tela")
public class AmpExplosionTela implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_explosion_tela")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idExplosionTela;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="id_pedido")
	private Long iPedido;
	
	@Column(name="id_tela")
	private Long idTela;
	
	@Column(name="fecha_explosion")
	private String fechaExplosion;
	
	@Column(name="cantidad_surtida")
	private String cantidadSurtida;
	
	@Column(name="cantidad_pendiente")
	private String cantidadPendiente;
	
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
	
	

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public Long getIdExplosionTela() {
		return idExplosionTela;
	}

	public void setIdExplosionTela(Long idExplosionTela) {
		this.idExplosionTela = idExplosionTela;
	}

	public Long getiPedido() {
		return iPedido;
	}

	public void setiPedido(Long iPedido) {
		this.iPedido = iPedido;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getFechaExplosion() {
		return fechaExplosion;
	}

	public void setFechaExplosion(String fechaExplosion) {
		this.fechaExplosion = fechaExplosion;
	}

	public String getCantidadSurtida() {
		return cantidadSurtida;
	}

	public void setCantidadSurtida(String cantidadSurtida) {
		this.cantidadSurtida = cantidadSurtida;
	}

	public String getCantidadPendiente() {
		return cantidadPendiente;
	}

	public void setCantidadPendiente(String cantidadPendiente) {
		this.cantidadPendiente = cantidadPendiente;
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

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
@Table(name = "alt_produccion_consumo_talla_entretela")
public class ProduccionConsumoTallaEntretela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_consumo")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idConsumo;
	
	@Column(name="id_prenda")
	private Long idPrenda;

	@Column(name="id_talla")
	private Long idTalla;
	
	@Column(name="consumo_largo")
	private String consumoLargo;
	
	@Column(name="consumo_ancho")
	private String consumoAncho;

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

	public Long getIdConsumo() {
		return idConsumo;
	}

	public void setIdConsumo(Long idConsumo) {
		this.idConsumo = idConsumo;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public Long getIdTalla() {
		return idTalla;
	}

	public void setIdTalla(Long idTalla) {
		this.idTalla = idTalla;
	}

	public String getConsumoLargo() {
		return consumoLargo;
	}

	public void setConsumoLargo(String consumoLargo) {
		this.consumoLargo = consumoLargo;
	}

	public String getConsumoAncho() {
		return consumoAncho;
	}

	public void setConsumoAncho(String consumoAncho) {
		this.consumoAncho = consumoAncho;
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

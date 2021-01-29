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
@Table(name = "alt_produccion_explosion_prendas")
public class ProduccionExplosionPrendas implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_explosion_prenda")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idExplosionPrenda;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="id_explosion_proceso")
	private Long idExplosionProceso;
	
	@Column(name="talla")
	private String talla;
	
	@Column(name="realizo")
	private String realizo;
	
	@Column(name="fecha_inicio")
	private String fechaInicio;
	
	@Column(name="fecha_fin")
	private String fechaFin;
	
	@Column(name="ubicacion")
	private String ubicacion;

	public Long getIdExplosionPrenda() {
		return idExplosionPrenda;
	}

	public void setIdExplosionPrenda(Long idExplosionPrenda) {
		this.idExplosionPrenda = idExplosionPrenda;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public Long getIdExplosionProceso() {
		return idExplosionProceso;
	}

	public void setIdExplosionProceso(Long idExplosionProceso) {
		this.idExplosionProceso = idExplosionProceso;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getRealizo() {
		return realizo;
	}

	public void setRealizo(String realizo) {
		this.realizo = realizo;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

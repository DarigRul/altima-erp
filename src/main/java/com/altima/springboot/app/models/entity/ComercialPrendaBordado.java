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
@Table(name = "alt_comercial_prenda_bordado")
public class ComercialPrendaBordado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_prenda_bordado")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idPrendaBordado;
	
	@Column(name="id_coordinado_prenda")
	private Long idCoordinadoPrenda;
	
	@Column(name="precio_bordado")
	private String precioBordado;
	
	@Column(name="archivo_bordado")
	private String archivoBordado;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	
	@Column(name="id_bordado")
	private Long idBordado;
	
	
	public Long getIdPrendaBordado() {
		return idPrendaBordado;
	}

	public void setIdPrendaBordado(Long idPrendaBordado) {
		this.idPrendaBordado = idPrendaBordado;
	}

	public Long getIdCoordinadoPrenda() {
		return idCoordinadoPrenda;
	}

	public void setIdCoordinadoPrenda(Long idCoordinadoPrenda) {
		this.idCoordinadoPrenda = idCoordinadoPrenda;
	}

	public String getPrecioBordado() {
		return precioBordado;
	}

	public void setPrecioBordado(String precioBordado) {
		this.precioBordado = precioBordado;
	}

	public String getArchivoBordado() {
		return archivoBordado;
	}

	public void setArchivoBordado(String archivoBordado) {
		this.archivoBordado = archivoBordado;
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

	public Long getIdBordado() {
		return idBordado;
	}

	public void setIdBordado(Long idBordado) {
		this.idBordado = idBordado;
	}
	
	
}

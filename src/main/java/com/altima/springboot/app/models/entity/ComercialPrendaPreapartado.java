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
@Table(name="alt_comercial_prendas_preapartado")
public class ComercialPrendaPreapartado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	@Id
	@Column(name="id_prenda_preapartado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdPrendaPreapartado;
	
	@Column(name="id_coordinado")
	private Long idCoordinado;
	
	@Column(name="id_familia_prenda")
	private Long idFamiliaPrenda;
	
	@Column(name="id_tela")
	private Long idTela;
	
	@Column(name="id_prenda")
	private Long idPrenda;

	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;

	public Long getIdPrendaPreapartado() {
		return IdPrendaPreapartado;
	}

	public void setIdPrendaPreapartado(Long idPrendaPreapartado) {
		IdPrendaPreapartado = idPrendaPreapartado;
	}

	public Long getIdCoordinado() {
		return idCoordinado;
	}

	public void setIdCoordinado(Long idCoordinado) {
		this.idCoordinado = idCoordinado;
	}
	
	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public Long getIdFamiliaPrenda() {
		return idFamiliaPrenda;
	}

	public void setIdFamiliaPrenda(Long idFamiliaPrenda) {
		this.idFamiliaPrenda = idFamiliaPrenda;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
package com.altima.springboot.app.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;

@Entity
@Table(name="alt_comercial_telas_preapartado")
public class ComercialTelasPreapartado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	@Id
	@Column(name="id_tela_preapartado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdTelaPreapartado;
	
	@Column(name="id_prenda_preapartado")
	private Long idPrendaPreapartado;
	
	@Column(name="id_tela")
	private Long idTela;
	
	@Column(name="id_material")
	private Long idMaterial;

	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;

	public Long getIdTelaPreapartado() {
		return IdTelaPreapartado;
	}

	public void setIdTelaPreapartado(Long idTelaPreapartado) {
		IdTelaPreapartado = idTelaPreapartado;
	}

	public Long getIdPrendaPreapartado() {
		return idPrendaPreapartado;
	}

	public void setIdPrendaPreapartado(Long idCoordinadoPreapartado) {
		this.idPrendaPreapartado = idCoordinadoPreapartado;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
	}

	public Long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
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

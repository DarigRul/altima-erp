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
@Table(name = "alt_produccion_consumo_talla")
public class ProduccionConsumoTalla implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_consumo_talla")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idConsumoTalla;
	
	@Column(name="id_talla")
	private Long idTalla;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="id_tipo_largo")
	private Long idTipoTalla;
	
	@Column(name="consumo")
	private String consumo;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus")
	private String Estatus;
	
	@Column(name="id_talla_especial")
	private Integer IdTallaEspecial;

	public Long getIdConsumoTalla() {
		return idConsumoTalla;
	}

	public void setIdConsumoTalla(Long idConsumoTalla) {
		this.idConsumoTalla = idConsumoTalla;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public Long getIdTipoTalla() {
		return idTipoTalla;
	}

	public void setIdTipoTalla(Long idTipoTalla) {
		this.idTipoTalla = idTipoTalla;
	}

	public String getConsumo() {
		return consumo;
	}

	public void setConsumo(String consumo) {
		this.consumo = consumo;
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
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdTalla() {
		return idTalla;
	}

	public void setIdTalla(Long idTalla) {
		this.idTalla = idTalla;
	}

	public Integer getIdTallaEspecial() {
		return IdTallaEspecial;
	}

	public void setIdTallaEspecial(Integer idTallaEspecial) {
		IdTallaEspecial = idTallaEspecial;
	}
	

}

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
@Table(name = "alt_servicio_cliente_lookup")
public class ServicioClienteLookup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_lookup")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idLookup;

	@Column(name="id_text")
	private String idText;
	
	@Column(name="nombre_lookup")
	private String nombreLookup;
	
	@Column(name="descripcion_lookup")
	private String descripcionLookup;
	
	@Column(name="tipo_lookup")
	private String tipoLookup;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus")
	private Integer estatus;
	
	@Column(name="atributo_1")
	private String atributo1;
	
	@Column(name="atributo_2")
	private String atributo2;

	@Column(name="atributo_3")
	private String atributo3;

	public Long getIdLookup() {
		return idLookup;
	}

	public void setIdLookup(Long idLookup) {
		this.idLookup = idLookup;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public String getNombreLookup() {
		return nombreLookup;
	}

	public void setNombreLookup(String nombreLookup) {
		this.nombreLookup = nombreLookup;
	}

	public String getDescripcionLookup() {
		return descripcionLookup;
	}

	public void setDescripcionLookup(String descripcionLookup) {
		this.descripcionLookup = descripcionLookup;
	}

	public String getTipoLookup() {
		return tipoLookup;
	}

	public void setTipoLookup(String tipoLookup) {
		this.tipoLookup = tipoLookup;
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

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public String getAtributo1() {
		return atributo1;
	}

	public void setAtributo1(String atributo1) {
		this.atributo1 = atributo1;
	}

	public String getAtributo2() {
		return atributo2;
	}

	public void setAtributo2(String atributo2) {
		this.atributo2 = atributo2;
	}

	public String getAtributo3() {
		return atributo3;
	}

	public void setAtributo3(String atributo3) {
		this.atributo3 = atributo3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}

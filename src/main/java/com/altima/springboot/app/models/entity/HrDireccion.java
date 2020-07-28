package com.altima.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alt_hr_direccion")

public class HrDireccion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_direccion")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idDireccion;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="municipio")
	private String municipio;
	
	@Column(name="colonia")
	private String colonia;
	
	@Column(name="calle")
	private String calle;
	
	@Column(name="numero_ext")
	private String NumeroExt;
	
	@Column(name="numero_int")
	private String NumeroInt;
	
	@Column(name="codigo_postal")
	private String codigoPostal;
	
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

	
	        
	
	
	public Long getIdDireccion() {
		return idDireccion;
	}





	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}





	public String getIdText() {
		return idText;
	}





	public void setIdText(String idText) {
		this.idText = idText;
	}





	public String getEstado() {
		return estado;
	}





	public void setEstado(String estado) {
		this.estado = estado;
	}





	public String getMunicipio() {
		return municipio;
	}





	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}





	public String getColonia() {
		return colonia;
	}





	public void setColonia(String colonia) {
		this.colonia = colonia;
	}





	public String getCalle() {
		return calle;
	}





	public void setCalle(String calle) {
		this.calle = calle;
	}





	public String getNumeroExt() {
		return NumeroExt;
	}





	public void setNumeroExt(String numeroExt) {
		NumeroExt = numeroExt;
	}





	public String getNumeroInt() {
		return NumeroInt;
	}





	public void setNumeroInt(String numeroInt) {
		NumeroInt = numeroInt;
	}





	public String getCodigoPostal() {
		return codigoPostal;
	}





	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
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





	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NumeroExt == null) ? 0 : NumeroExt.hashCode());
		result = prime * result + ((NumeroInt == null) ? 0 : NumeroInt.hashCode());
		result = prime * result + ((actualizadoPor == null) ? 0 : actualizadoPor.hashCode());
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((colonia == null) ? 0 : colonia.hashCode());
		result = prime * result + ((creadoPor == null) ? 0 : creadoPor.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((estatus == null) ? 0 : estatus.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((idDireccion == null) ? 0 : idDireccion.hashCode());
		result = prime * result + ((idText == null) ? 0 : idText.hashCode());
		result = prime * result + ((municipio == null) ? 0 : municipio.hashCode());
		result = prime * result + ((ultimaFechaModificacion == null) ? 0 : ultimaFechaModificacion.hashCode());
		return result;
	}


	
}

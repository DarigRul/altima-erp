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
@Table(name = "alt_comercial_bordado")
public class ComercialBordado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_bordado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idBordado;
	
	@Column(name="id_cliente")
	private Long idCliente;
	
	@Column(name="id_lookup")
	private Long idLookup;
	
	@Column(name="tamaño")
	private String tamano;
	
	@Column(name="precio")
	private String precio;
	
	@Column(name="ruta_bordado")
	private String rutaBordado;
	
	@Column(name="ruta_ponchado")
	private String rutaPonchado;
	
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
	
	@Column(name="estatus_bordado")
	private String estatus_bordado;
	
	
	@Column(name="descripcion")
	private String  descripcion ;

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdBordado() {
		return idBordado;
	}

	public void setIdBordado(Long idBordado) {
		this.idBordado = idBordado;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdLookup() {
		return idLookup;
	}

	public void setIdLookup(Long idLookup) {
		this.idLookup = idLookup;
	}



	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getRutaBordado() {
		return rutaBordado;
	}

	public void setRutaBordado(String rutaBordado) {
		this.rutaBordado = rutaBordado;
	}

	public String getRutaPonchado() {
		return rutaPonchado;
	}

	public void setRutaPonchado(String rutaPonchado) {
		this.rutaPonchado = rutaPonchado;
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

	public String getEstatus_bordado() {
		return estatus_bordado;
	}

	public void setEstatus_bordado(String estatus_bordado) {
		this.estatus_bordado = estatus_bordado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

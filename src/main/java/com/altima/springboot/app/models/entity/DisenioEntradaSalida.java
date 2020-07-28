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
@Table(name = "alt_disenio_entrada_salida")
public class DisenioEntradaSalida implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_entrada_salida")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idEntradaSalida;

	@Column(name="id_muestrario")
	private Long idMuestrario;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	

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

	public Long getIdEntradaSalida() {
		return idEntradaSalida;
	}

	public void setIdEntradaSalida(Long idEntradaSalida) {
		this.idEntradaSalida = idEntradaSalida;
	}

	public Long getIdMuestrario() {
		return idMuestrario;
	}

	public void setIdMuestrario(Long idMuestrario) {
		this.idMuestrario = idMuestrario;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEntradaSalida == null) ? 0 : idEntradaSalida.hashCode());
		result = prime * result + ((idMuestrario == null) ? 0 : idMuestrario.hashCode());
		result = prime * result + ((idText == null) ? 0 : idText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisenioEntradaSalida other = (DisenioEntradaSalida) obj;
		if (idEntradaSalida == null) {
			if (other.idEntradaSalida != null)
				return false;
		} else if (!idEntradaSalida.equals(other.idEntradaSalida))
			return false;
		if (idMuestrario == null) {
			if (other.idMuestrario != null)
				return false;
		} else if (!idMuestrario.equals(other.idMuestrario))
			return false;
		if (idText == null) {
			if (other.idText != null)
				return false;
		} else if (!idText.equals(other.idText))
			return false;
		return true;
	}
}

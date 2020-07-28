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
@Table(name = "alt_disenio_forro")
public class DisenioForro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_forro")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idForro;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name = "id_text_prospecto")
	private String idTextProspecto;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="descripcion_forro")
	private String descripcionForro;
	
	@Column(name="nombre_forro")
	private String nombreForro;
	
	@Column(name="ancho_forro")
	private String anchoForro;
	
	@Column(name="color")
	private String color;
	
	@Column(name="codigo_color")
	private String codigo_coolor;
	
	@Column(name="id_unidad_medida")
	private Long idUnidadMedida;
	
	@Column(name="existencia_forro")
	private String existenciaForro;
	
	@Column(name="foto")
	private String foto;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="estatus_forro")
	private String estatusForro;
	
	

	
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCodigo_coolor() {
		return codigo_coolor;
	}

	public void setCodigo_coolor(String codigo_coolor) {
		this.codigo_coolor = codigo_coolor;
	}

	public String getIdTextProspecto() {
		return idTextProspecto;
	}

	public void setIdTextProspecto(String idTextProspecto) {
		this.idTextProspecto = idTextProspecto;
	}

	public Long getIdForro() {
		return idForro;
	}

	public void setIdForro(Long idForro) {
		this.idForro = idForro;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
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

	public String getDescripcionForro() {
		return descripcionForro;
	}

	public void setDescripcionForro(String descripcionForro) {
		this.descripcionForro = descripcionForro;
	}

	public String getNombreForro() {
		return nombreForro;
	}

	public void setNombreForro(String nombreForro) {
		this.nombreForro = nombreForro;
	}

	public String getAnchoForro() {
		return anchoForro;
	}

	public void setAnchoForro(String anchoForro) {
		this.anchoForro = anchoForro;
	}

	public Long getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(Long idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public String getExistenciaForro() {
		return existenciaForro;
	}

	public void setExistenciaForro(String existenciaForro) {
		this.existenciaForro = existenciaForro;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusForro() {
		return estatusForro;
	}

	public void setEstatusForro(String estatusForro) {
		this.estatusForro = estatusForro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualizadoPor == null) ? 0 : actualizadoPor.hashCode());
		result = prime * result + ((anchoForro == null) ? 0 : anchoForro.hashCode());
		result = prime * result + ((creadoPor == null) ? 0 : creadoPor.hashCode());
		result = prime * result + ((descripcionForro == null) ? 0 : descripcionForro.hashCode());
		result = prime * result + ((estatus == null) ? 0 : estatus.hashCode());
		result = prime * result + ((estatusForro == null) ? 0 : estatusForro.hashCode());
		result = prime * result + ((existenciaForro == null) ? 0 : existenciaForro.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((foto == null) ? 0 : foto.hashCode());
		result = prime * result + ((idForro == null) ? 0 : idForro.hashCode());
		result = prime * result + ((idText == null) ? 0 : idText.hashCode());
		result = prime * result + ((idUnidadMedida == null) ? 0 : idUnidadMedida.hashCode());
		result = prime * result + ((nombreForro == null) ? 0 : nombreForro.hashCode());
		result = prime * result + ((ultimaFechaModificacion == null) ? 0 : ultimaFechaModificacion.hashCode());
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
		DisenioForro other = (DisenioForro) obj;
		if (actualizadoPor == null) {
			if (other.actualizadoPor != null)
				return false;
		} else if (!actualizadoPor.equals(other.actualizadoPor))
			return false;
		if (anchoForro == null) {
			if (other.anchoForro != null)
				return false;
		} else if (!anchoForro.equals(other.anchoForro))
			return false;
		if (creadoPor == null) {
			if (other.creadoPor != null)
				return false;
		} else if (!creadoPor.equals(other.creadoPor))
			return false;
		if (descripcionForro == null) {
			if (other.descripcionForro != null)
				return false;
		} else if (!descripcionForro.equals(other.descripcionForro))
			return false;
		if (estatus == null) {
			if (other.estatus != null)
				return false;
		} else if (!estatus.equals(other.estatus))
			return false;
		if (estatusForro == null) {
			if (other.estatusForro != null)
				return false;
		} else if (!estatusForro.equals(other.estatusForro))
			return false;
		if (existenciaForro == null) {
			if (other.existenciaForro != null)
				return false;
		} else if (!existenciaForro.equals(other.existenciaForro))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (foto == null) {
			if (other.foto != null)
				return false;
		} else if (!foto.equals(other.foto))
			return false;
		if (idForro == null) {
			if (other.idForro != null)
				return false;
		} else if (!idForro.equals(other.idForro))
			return false;
		if (idText == null) {
			if (other.idText != null)
				return false;
		} else if (!idText.equals(other.idText))
			return false;
		if (idUnidadMedida == null) {
			if (other.idUnidadMedida != null)
				return false;
		} else if (!idUnidadMedida.equals(other.idUnidadMedida))
			return false;
		if (nombreForro == null) {
			if (other.nombreForro != null)
				return false;
		} else if (!nombreForro.equals(other.nombreForro))
			return false;
		if (ultimaFechaModificacion == null) {
			if (other.ultimaFechaModificacion != null)
				return false;
		} else if (!ultimaFechaModificacion.equals(other.ultimaFechaModificacion))
			return false;
		return true;
	}
}

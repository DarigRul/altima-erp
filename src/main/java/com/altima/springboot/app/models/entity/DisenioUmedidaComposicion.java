package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "alt_disenio_umedida_composicion")
public class DisenioUmedidaComposicion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_umedida_composicion")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idUmedida_Composicion;
	
	@Column(name="id_unidad_medida")
	@NotBlank
	private Long idUnidadMedida;
	
	@Column(name="id_composicion")
	@NotBlank
	private Long idComposicion;

	public Long getIdUmedida_Composicion() {
		return idUmedida_Composicion;
	}

	public void setIdUmedida_Composicion(Long idUmedida_Composicion) {
		this.idUmedida_Composicion = idUmedida_Composicion;
	}

	public Long getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(Long idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public Long getIdComposicion() {
		return idComposicion;
	}

	public void setIdComposicion(Long idComposicion) {
		this.idComposicion = idComposicion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idComposicion == null) ? 0 : idComposicion.hashCode());
		result = prime * result + ((idUmedida_Composicion == null) ? 0 : idUmedida_Composicion.hashCode());
		result = prime * result + ((idUnidadMedida == null) ? 0 : idUnidadMedida.hashCode());
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
		DisenioUmedidaComposicion other = (DisenioUmedidaComposicion) obj;
		if (idComposicion == null) {
			if (other.idComposicion != null)
				return false;
		} else if (!idComposicion.equals(other.idComposicion))
			return false;
		if (idUmedida_Composicion == null) {
			if (other.idUmedida_Composicion != null)
				return false;
		} else if (!idUmedida_Composicion.equals(other.idUmedida_Composicion))
			return false;
		if (idUnidadMedida == null) {
			if (other.idUnidadMedida != null)
				return false;
		} else if (!idUnidadMedida.equals(other.idUnidadMedida))
			return false;
		return true;
	}
}

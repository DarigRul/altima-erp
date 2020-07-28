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
@Table(name = "alt_disenio_umedida_icuidado")
public class DisenioUmedidaIcuidado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_umedida_icuidado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idUmedida_Composicion;
	
	@Column(name="id_unidad_medida")
	@NotBlank
	private Long idUnidadMedida;
	
	@Column(name="id_instrucciones_cuidado")
	@NotBlank
	private Long idInstruccionesCuidado;

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

	public Long getIdInstruccionesCuidado() {
		return idInstruccionesCuidado;
	}

	public void setIdInstruccionesCuidado(Long idInstruccionesCuidado) {
		this.idInstruccionesCuidado = idInstruccionesCuidado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInstruccionesCuidado == null) ? 0 : idInstruccionesCuidado.hashCode());
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
		DisenioUmedidaIcuidado other = (DisenioUmedidaIcuidado) obj;
		if (idInstruccionesCuidado == null) {
			if (other.idInstruccionesCuidado != null)
				return false;
		} else if (!idInstruccionesCuidado.equals(other.idInstruccionesCuidado))
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

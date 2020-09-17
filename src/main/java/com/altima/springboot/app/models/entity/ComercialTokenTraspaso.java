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
@Table(name = "alt_comercial_token_traspaso")
public class ComercialTokenTraspaso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="codigo_traspaso")
	private Long codigoTraspaso;
	
	@Column(name="id_agente")
	private Long idAgente;

	public Long getCodigoTraspaso() {
		return codigoTraspaso;
	}

	public void setCodigoTraspaso(Long codigoTraspaso2) {
		this.codigoTraspaso = codigoTraspaso2;
	}

	public Long getIdAgente() {
		return idAgente;
	}

	public void setIdAgente(Long idAgente) {
		this.idAgente = idAgente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
	
	
}

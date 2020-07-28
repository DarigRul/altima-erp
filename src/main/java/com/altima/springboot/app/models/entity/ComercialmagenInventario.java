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
@Table(name = "alt_comercial_imagen_inventario")
public class ComercialmagenInventario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_imagen_inventario")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idImagenInventario;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="id_tela")
	private Long idTela;
	
	@Column(name="ruta_prenda")
	private String rutaPrenda;

	public Long getIdImagenInventario() {
		return idImagenInventario;
	}

	public void setIdImagenInventario(Long idImagenInventario) {
		this.idImagenInventario = idImagenInventario;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
	}

	public String getRutaPrenda() {
		return rutaPrenda;
	}

	public void setRutaPrenda(String rutaPrenda) {
		this.rutaPrenda = rutaPrenda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

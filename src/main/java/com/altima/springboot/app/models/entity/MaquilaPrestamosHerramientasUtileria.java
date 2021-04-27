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
@Table(name = "alt_maquila_prestamos_herramientas_utileria")
public class MaquilaPrestamosHerramientasUtileria implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_maquila_prestamo")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idMaquilaPrestamo;
	
	@Column(name="id_herramienta")
	private Long idHerramienta;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="folio")
	private String folio;
	
	@Column(name="fecha")
	private String fecha;
	
	@Column(name="id_operario")
	private Long idOperario;

	public Long getIdMaquilaPrestamo() {
		return idMaquilaPrestamo;
	}

	public void setIdMaquilaPrestamo(Long idMaquilaPrestamo) {
		this.idMaquilaPrestamo = idMaquilaPrestamo;
	}

	public Long getIdHerramienta() {
		return idHerramienta;
	}

	public void setIdHerramienta(Long idHerramienta) {
		this.idHerramienta = idHerramienta;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Long getIdOperario() {
		return idOperario;
	}

	public void setIdOperario(Long idOperario) {
		this.idOperario = idOperario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	


}

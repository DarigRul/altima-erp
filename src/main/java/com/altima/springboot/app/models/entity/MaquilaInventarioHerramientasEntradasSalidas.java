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
@Table(name = "alt_maquila_inventario_herramienta_entradas_salidas")
public class MaquilaInventarioHerramientasEntradasSalidas implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_inventario_herramienta")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idInventarioHerramienta;

	@Column(name="id_herramienta")
	private Long idHerramienta;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="id_movimiento")
	private Long idMovimiento;
	
	@Column(name="folio")
	private String folio;
	
	@Column(name="fecha")
	private String fecha;

	public Long getIdInventarioHerramienta() {
		return idInventarioHerramienta;
	}

	public void setIdInventarioHerramienta(Long idInventarioHerramienta) {
		this.idInventarioHerramienta = idInventarioHerramienta;
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

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	



}

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
@Table(name = "alt_amp_explosion_materiales")
public class AmpExplosionMateriales implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_explosion_material")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idExplosionMaterial;

	@Column(name="id_pedido")
	private Long idPedido;
	
	@Column(name="estatus")
	private String Estatus;
	
	@Column(name="fecha_recepcion")
	private String FechaRecepcion;
	
	@Column(name="fecha_explosion")
	private String FechaExplosion;
	
	@Column(name="fecha_habilitacion")
	private String FechaHabilitacion;

	public Long getIdExplosionMaterial() {
		return idExplosionMaterial;
	}

	public void setIdExplosionMaterial(Long idExplosionMaterial) {
		this.idExplosionMaterial = idExplosionMaterial;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public String getFechaRecepcion() {
		return FechaRecepcion;
	}

	public void setFechaRecepcion(String fechaRecepcion) {
		FechaRecepcion = fechaRecepcion;
	}

	public String getFechaExplosion() {
		return FechaExplosion;
	}

	public void setFechaExplosion(String fechaExplosion) {
		FechaExplosion = fechaExplosion;
	}

	public String getFechaHabilitacion() {
		return FechaHabilitacion;
	}

	public void setFechaHabilitacion(String fechaHabilitacion) {
		FechaHabilitacion = fechaHabilitacion;
	}
	
	
	
	
	
	
}

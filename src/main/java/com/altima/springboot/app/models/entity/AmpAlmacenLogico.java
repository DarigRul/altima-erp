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
@Table(name = "alt_amp_almacen_logico")
public class AmpAlmacenLogico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_almacen_logico")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idAlmacenLogico;
	
	@Column(name="id_almacen_fisico")
	private Long idAlmacenFisico;
	
	@Column(name="id_movimiento_entrada")
	private Long idMovimientoEntrada;
	
	@Column(name="id_movimiento_salida")
	private Long idMovimientoSalida;
	
	@Column(name="nombre_almacen_logico")
	private String nombreAlmacenLogico;
	
	@Column(name="tipo")
	private String tipo;
	
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
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdAlmacenLogico() {
		return idAlmacenLogico;
	}

	public void setIdAlmacenLogico(Long idAlmacenLogico) {
		this.idAlmacenLogico = idAlmacenLogico;
	}

	public Long getIdAlmacenFisico() {
		return idAlmacenFisico;
	}

	public void setIdAlmacenFisico(Long idAlmacenFisico) {
		this.idAlmacenFisico = idAlmacenFisico;
	}

	public Long getIdMovimientoEntrada() {
		return idMovimientoEntrada;
	}

	public void setIdMovimientoEntrada(Long idMovimientoEntrada) {
		this.idMovimientoEntrada = idMovimientoEntrada;
	}

	public Long getIdMovimientoSalida() {
		return idMovimientoSalida;
	}

	public void setIdMovimientoSalida(Long idMovimientoSalida) {
		this.idMovimientoSalida = idMovimientoSalida;
	}

	public String getNombreAlmacenLogico() {
		return nombreAlmacenLogico;
	}

	public void setNombreAlmacenLogico(String nombreAlmacenLogico) {
		this.nombreAlmacenLogico = nombreAlmacenLogico;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}

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
@Table(name = "alt_servicio_cliente_arreglo_prenda")
public class ServicioClienteArregloPrenda implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_arreglo_prendas")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idArregloPren;

	@Column(name="id_arreglo")
	private String idArreglo;

	@Column(name="id_prenda")
	private String idPrenda;

	@Column(name="id_complejidad")
	private String idComplejidad;

	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdArregloPren() {
		return idArregloPren;
	}

	public void setIdArregloPren(Long idArregloPren) {
		this.idArregloPren = idArregloPren;
	}

	public String getIdArreglo() {
		return idArreglo;
	}

	public void setIdArreglo(String idArreglo) {
		this.idArreglo = idArreglo;
	}

	public String getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(String idPrenda) {
		this.idPrenda = idPrenda;
	}

	public String getIdComplejidad() {
		return idComplejidad;
	}

	public void setIdComplejidad(String idComplejidad) {
		this.idComplejidad = idComplejidad;
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

	

    
}

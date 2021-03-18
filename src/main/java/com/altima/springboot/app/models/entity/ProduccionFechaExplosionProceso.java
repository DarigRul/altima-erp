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
@Table(name = "alt_produccion_fecha_explosion")
public class ProduccionFechaExplosionProceso {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_fecha_explosion_proceso")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private Long idFechaExplosionProceso;

    @Column(name="id_fecha")
    private String id_fecha;
    
    @Column(name="id_explosion_proceso")
    private String id_explosion_proceso;

    @Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_actualizacion")
	private String ultimaFechaActualizacion;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdFechaExplosionProceso() {
		return idFechaExplosionProceso;
	}

	public void setIdFechaExplosionProceso(Long idFechaExplosionProceso) {
		this.idFechaExplosionProceso = idFechaExplosionProceso;
	}

	public String getId_fecha() {
		return id_fecha;
	}

	public void setId_fecha(String id_fecha) {
		this.id_fecha = id_fecha;
	}

	public String getId_explosion_proceso() {
		return id_explosion_proceso;
	}

	public void setId_explosion_proceso(String id_explosion_proceso) {
		this.id_explosion_proceso = id_explosion_proceso;
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

	public String getUltimaFechaActualizacion() {
		return ultimaFechaActualizacion;
	}

	public void setUltimaFechaActualizacion(String ultimaFechaActualizacion) {
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
	}

	


    
}

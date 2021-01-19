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
@Table(name = "alt_produccion_fecha_coordinado_prenda")
public class ProduccionFechaCoordinadoPrenda {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_fecha_coordinado_prenda")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private Long idFechaCoordinadoPrenda;
    
    @Column(name="id_fecha")
    private String id_fecha;
    
    @Column(name="id_coordinado_prenda")
    private String id_coordinado_prenda;
    
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

    public Long getIdFechaCoordinadoPrenda() {
        return idFechaCoordinadoPrenda;
    }

    public void setIdFechaCoordinadoPrenda(Long idFechaCoordinadoPrenda) {
        this.idFechaCoordinadoPrenda = idFechaCoordinadoPrenda;
    }

    public String getId_fecha() {
        return id_fecha;
    }

    public void setId_fecha(String id_fecha) {
        this.id_fecha = id_fecha;
    }

    public String getId_coordinado_prenda() {
        return id_coordinado_prenda;
    }

    public void setId_coordinado_prenda(String id_coordinado_prenda) {
        this.id_coordinado_prenda = id_coordinado_prenda;
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

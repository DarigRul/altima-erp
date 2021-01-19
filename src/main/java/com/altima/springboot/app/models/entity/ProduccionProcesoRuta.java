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
@Table(name = "alt_produccion_proceso_ruta")
public class ProduccionProcesoRuta  implements Serializable{

    private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_ruta_proceso")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idProduccionPedidoColeccion;
	
	@Column(name="id_lookup_ruta")
    private Long idLookupRuta;
    
    @Column(name="id_lookup_proceso")
    private Long idLookupProceso;

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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdProduccionPedidoColeccion() {
        return idProduccionPedidoColeccion;
    }

    public void setIdProduccionPedidoColeccion(Long idProduccionPedidoColeccion) {
        this.idProduccionPedidoColeccion = idProduccionPedidoColeccion;
    }

    public Long getIdLookupRuta() {
        return idLookupRuta;
    }

    public void setIdLookupRuta(Long idLookupRuta) {
        this.idLookupRuta = idLookupRuta;
    }

    public Long getIdLookupProceso() {
        return idLookupProceso;
    }

    public void setIdLookupProceso(Long idLookupProceso) {
        this.idLookupProceso = idLookupProceso;
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
    
    
    
}

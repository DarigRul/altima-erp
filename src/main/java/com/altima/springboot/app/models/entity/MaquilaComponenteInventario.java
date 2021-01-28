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
@Table(name = "alt_maquila_componente_inventario")
public class MaquilaComponenteInventario implements Serializable {
    
    private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_componente_inventario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
    private Long idComponenteInventario;

    @Column(name="id_lookup")
    private String idLookup;
    

    @Column(name="id_inventario")
    private String idInventario;
    

    @Column(name="marca")
    private String marca;
    

    @Column(name="cantidad")
    private String cantidad;
    
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

    public Long getIdComponenteInventario() {
        return idComponenteInventario;
    }

    public void setIdComponenteInventario(Long idComponenteInventario) {
        this.idComponenteInventario = idComponenteInventario;
    }

    public String getIdLookup() {
        return idLookup;
    }

    public void setIdLookup(String idLookup) {
        this.idLookup = idLookup;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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

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
@Table(name = "alt_maquila_inventario_activo_fijo")
public class MaquilaInventarioActivoFijo implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_inventario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
    private Long idInventario;
    
    @Column(name="clave")
    private String clave;
    
    @Column(name="id_lookup")
    private String idLookup;
    
    @Column(name="activo_fijo")
    private String activoFijo;
    
    @Column(name="modelo")
    private String modelo;
    
    @Column(name="marca")
    private String marca;
    
    @Column(name="serie")
    private String serie;
    
    @Column(name="motor")
    private String motor;
    
    @Column(name="fecha_ingreso")
    private String fechaIngresa;
    
    @Column(name="fecha_bajo")
    private String fechaBajo;

    //hhhhhhhhhhh
    @Column(name="estatus")
    private String estatus;

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

    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getIdLookup() {
        return idLookup;
    }

    public void setIdLookup(String idLookup) {
        this.idLookup = idLookup;
    }

    public String getActivoFijo() {
        return activoFijo;
    }

    public void setActivoFijo(String activoFijo) {
        this.activoFijo = activoFijo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getFechaIngresa() {
        return fechaIngresa;
    }

    public void setFechaIngresa(String fechaIngresa) {
        this.fechaIngresa = fechaIngresa;
    }

    public String getFechaBajo() {
        return fechaBajo;
    }

    public void setFechaBajo(String fechaBajo) {
        this.fechaBajo = fechaBajo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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

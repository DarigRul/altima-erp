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
@Table(name="alt_soporte_tecnico_inventario")
public class SoporteTecnicoInventario implements Serializable {
    
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_inventario_equipo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
    private Long idInventarioEquipo;

    @Column(name="provedor")
	private String provedor;

    @Column(name="tipo")
	private String tipo;

    @Column(name="fecha")
	private String fecha;

    @Column(name="marca")
	private String marca;

    @Column(name="modelo")
	private String modelo;

    @Column(name="serie")
	private String serie;

    @Column(name="procesador")
	private String procesador;

    @Column(name="ram")
	private String ram;

    @Column(name="disco_duro")
	private String discoDuro;

    @Column(name="pantalla")
	private String pantalla;

    @Column(name="ns_pantalla")
	private String nsPantalla;

    @Column(name="direccionIp")
	private String direccion_ip;

    @Column(name="asignado_a")
	private String asignadoA;

    @Column(name="observaciones")
	private String observaciones;

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

    public Long getIdInventarioEquipo() {
        return idInventarioEquipo;
    }

    public void setIdInventarioEquipo(Long idInventarioEquipo) {
        this.idInventarioEquipo = idInventarioEquipo;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
        this.provedor = provedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getNsPantalla() {
        return nsPantalla;
    }

    public void setNsPantalla(String nsPantalla) {
        this.nsPantalla = nsPantalla;
    }

    public String getDireccion_ip() {
        return direccion_ip;
    }

    public void setDireccion_ip(String direccion_ip) {
        this.direccion_ip = direccion_ip;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    
}

package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RequisicionListDto implements Serializable{
    

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long idMaterial;
    private Long idProveedor;
    private String idRequisicionAlmacenMaterial;
    private String idTextRequisicion;
    private String idTextMaterial;
    private String fechaCreacion;
    private String nombreMaterial;
    private String nombreUsuario;
    private String nombreProveedor;
    private String modelo;
    private String color;
    private float cantidad;
    private float precio;
    private Long idColor;
    @Id
    private String tipo;



    public String getIdRequisicionAlmacenMaterial() {
        return idRequisicionAlmacenMaterial;
    }

    public void setIdRequisicionAlmacenMaterial(String idRequisicionAlmacenMaterial) {
        this.idRequisicionAlmacenMaterial = idRequisicionAlmacenMaterial;
    }

    public String getIdTextRequisicion() {
        return idTextRequisicion;
    }

    public void setIdTextRequisicion(String idTextRequisicion) {
        this.idTextRequisicion = idTextRequisicion;
    }

    public String getIdTextMaterial() {
        return idTextMaterial;
    }

    public void setIdTextMaterial(String idTextMaterial) {
        this.idTextMaterial = idTextMaterial;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }
}

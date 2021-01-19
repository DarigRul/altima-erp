package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InventarioListDto implements Serializable{

	/**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id_material;
    private String id_text;
    private String nombre;
    private String medida;
    private String tamanio;
    private String color;
    private float precio;
    private String almacen;
    @Id
    private String tipo;

    public Long getId_material() {
        return id_material;
    }

    public void setId_material(Long id_material) {
        this.id_material = id_material;
    }

    public String getId_text() {
        return id_text;
    }

    public void setId_text(String id_text) {
        this.id_text = id_text;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }
    
}

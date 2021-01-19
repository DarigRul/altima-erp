package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrdenCompraDetalleDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long idOrdenComprasDetalle;

    private Long idOrdenCompras;

    private Long idMaterial;

    private Long idColor;

    private String tipoMaterial;

    private float cantidad;

    private String actualizadoPor;

    private String fechaCreacion;

    private String ultimaFechaModificacion;

    private String creadoPor;

    private float precioUnitario;

    private float montoCargoDescuento;

    private String idText;

    private String nombre;

    private String color;

    public Long getIdOrdenComprasDetalle() {
        return idOrdenComprasDetalle;
    }

    public void setIdOrdenComprasDetalle(Long idOrdenComprasDetalle) {
        this.idOrdenComprasDetalle = idOrdenComprasDetalle;
    }

    public Long getIdOrdenCompras() {
        return idOrdenCompras;
    }

    public void setIdOrdenCompras(Long idOrdenCompras) {
        this.idOrdenCompras = idOrdenCompras;
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

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
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

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getMontoCargoDescuento() {
        return montoCargoDescuento;
    }

    public void setMontoCargoDescuento(float montoCargoDescuento) {
        this.montoCargoDescuento = montoCargoDescuento;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}

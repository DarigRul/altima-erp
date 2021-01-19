package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrdenComprasListDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long idOrdenCompras;
    private String idText;
    private String fechaCreacion;
    private String nombreProveedor;
    private String estatus;

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Long getIdOrdenCompras() {
        return idOrdenCompras;
    }

    public void setIdOrdenCompras(Long idOrdenCompras) {
        this.idOrdenCompras = idOrdenCompras;
    }

    
}

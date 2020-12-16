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
@Table(name = "alt_amp_tela_faltante")
public class AmpTelaFaltante implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_tela_faltante")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long idTelaFaltante;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_tela")
    private Long idTela;

    @Column(name = "cantidad")
    private float cantidad;

    @Column(name = "fecha_promesa")
    private String fechaPromesa;

    @Column(name = "estatus")
    private int estatus;

    @Column(name = "creado_por")
    private String creadoPor;

    @Column(name = "actualizado_por")
    private String actualizadoPor;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "ultimaFechaModificacion")
    private String ultimaFechaModificacion;

    public Long getIdTelaFaltante() {
        return idTelaFaltante;
    }

    public void setIdTelaFaltante(Long idTelaFaltante) {
        this.idTelaFaltante = idTelaFaltante;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdTela() {
        return idTela;
    }

    public void setIdTela(Long idTela) {
        this.idTela = idTela;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaPromesa() {
        return fechaPromesa;
    }

    public void setFechaPromesa(String fechaPromesa) {
        this.fechaPromesa = fechaPromesa;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
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

    @Override
    public String toString() {
        return "AmpTelaFaltante [actualizadoPor=" + actualizadoPor + ", cantidad=" + cantidad + ", creadoPor="
                + creadoPor + ", estatus=" + estatus + ", fechaCreacion=" + fechaCreacion + ", fechaPromesa="
                + fechaPromesa + ", idPedido=" + idPedido + ", idTela=" + idTela + ", idTelaFaltante=" + idTelaFaltante
                + ", ultimaFechaModificacion=" + ultimaFechaModificacion + "]";
    }

}

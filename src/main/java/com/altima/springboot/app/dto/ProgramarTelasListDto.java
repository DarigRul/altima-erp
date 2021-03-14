package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class ProgramarTelasListDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long idCoordinadoPrenda;
    private String idTextPedido;
    private String fechaEntrega;
    private String programa;
    private int numeroCoordinado;
    private String idTextPrenda;
    private String familiaPrenda;
    private String bordado;
    private String ruta;
    private int confeccion;
    private String idTextTela;
    private float ancho;
    private String familiaComposicion;
    private float pruebaEncogimientoLargo;
    private String estampado;
    private String estatus;
    private String folio;

    public Long getIdCoordinadoPrenda() {
        return idCoordinadoPrenda;
    }

    public void setIdCoordinadoPrenda(Long idCoordinadoPrenda) {
        this.idCoordinadoPrenda = idCoordinadoPrenda;
    }

    public String getIdTextPedido() {
        return idTextPedido;
    }

    public void setIdTextPedido(String idTextPedido) {
        this.idTextPedido = idTextPedido;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getNumeroCoordinado() {
        return numeroCoordinado;
    }

    public void setNumeroCoordinado(int numeroCoordinado) {
        this.numeroCoordinado = numeroCoordinado;
    }

    public String getIdTextPrenda() {
        return idTextPrenda;
    }

    public void setIdTextPrenda(String idTextPrenda) {
        this.idTextPrenda = idTextPrenda;
    }

    public String getFamiliaPrenda() {
        return familiaPrenda;
    }

    public void setFamiliaPrenda(String familiaPrenda) {
        this.familiaPrenda = familiaPrenda;
    }

    public String getBordado() {
        return bordado;
    }

    public void setBordado(String bordado) {
        this.bordado = bordado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getConfeccion() {
        return confeccion;
    }

    public void setConfeccion(int confeccion) {
        this.confeccion = confeccion;
    }

    public String getIdTextTela() {
        return idTextTela;
    }

    public void setIdTextTela(String idTextTela) {
        this.idTextTela = idTextTela;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public String getFamiliaComposicion() {
        return familiaComposicion;
    }

    public void setFamiliaComposicion(String familiaComposicion) {
        this.familiaComposicion = familiaComposicion;
    }

    public float getPruebaEncogimientoLargo() {
        return pruebaEncogimientoLargo;
    }

    public void setPruebaEncogimientoLargo(float pruebaEncogimientoLargo) {
        this.pruebaEncogimientoLargo = pruebaEncogimientoLargo;
    }

    public String getEstampado() {
        return estampado;
    }

    public void setEstampado(String estampado) {
        this.estampado = estampado;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

}

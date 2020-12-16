package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PrendaListDTO {
    @Id
    private Long idPrenda;
    private String idText;
    private String idTextProspecto;
    private String descripcionPrenda;
    private String tipoPrenda;
    private int prendaLocal;
    private String estatusRecepcionMuestra;
    private String estatus;
    private Boolean mostrar;
	private String fechaRecepcionProduccion;
	private String fechaDevolucionProduccion;

    public Long getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(Long idPrenda) {
        this.idPrenda = idPrenda;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getDescripcionPrenda() {
        return descripcionPrenda;
    }

    public void setDescripcionPrenda(String descripcionPrenda) {
        this.descripcionPrenda = descripcionPrenda;
    }

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public int getPrendaLocal() {
        return prendaLocal;
    }

    public void setPrendaLocal(int prendaLocal) {
        this.prendaLocal = prendaLocal;
    }

    public String getEstatusRecepcionMuestra() {
        return estatusRecepcionMuestra;
    }

    public void setEstatusRecepcionMuestra(String estatusRecepcionMuestra) {
        this.estatusRecepcionMuestra = estatusRecepcionMuestra;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getIdTextProspecto() {
        return idTextProspecto;
    }

    public void setIdTextProspecto(String idTextProspecto) {
        this.idTextProspecto = idTextProspecto;
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public String getFechaRecepcionProduccion() {
        return fechaRecepcionProduccion;
    }

    public void setFechaRecepcionProduccion(String fechaRecepcionProduccion) {
        this.fechaRecepcionProduccion = fechaRecepcionProduccion;
    }

    public String getFechaDevolucionProduccion() {
        return fechaDevolucionProduccion;
    }

    public void setFechaDevolucionProduccion(String fechaDevolucionProduccion) {
        this.fechaDevolucionProduccion = fechaDevolucionProduccion;
    }

}

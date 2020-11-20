package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ExplosionTelaDto {
    
    @Id
    private String idText;
    private String nombreTela;
    private float consumo;
    private float spfConsumo;
    private float disponible;
    private float apartado;
    private Long idTela;

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getNombreTela() {
        return nombreTela;
    }

    public void setNombreTela(String nombreTela) {
        this.nombreTela = nombreTela;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    public float getSpfConsumo() {
        return spfConsumo;
    }

    public void setSpfConsumo(float spfConsumo) {
        this.spfConsumo = spfConsumo;
    }

    public float getDisponible() {
        return disponible;
    }

    public void setDisponible(float disponible) {
        this.disponible = disponible;
    }

    public float getApartado() {
        return apartado;
    }

    public void setApartado(float apartado) {
        this.apartado = apartado;
    }

    public Long getIdTela() {
        return idTela;
    }

    public void setIdTela(Long idTela) {
        this.idTela = idTela;
    }

    

}

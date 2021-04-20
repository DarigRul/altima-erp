package com.altima.springboot.app.dto;

public class TiemposProcesosDto {
    
    private Long idLookup;
    private Long idTiempoFamiliaPrenda;
    private String familiaPrenda;
    private int tiempoTalla;
    private int tiempoPrendaLisa;
    private int tiempoPrendaCuadros;
    private int tiempoPrendaFantasia;
    private int tiempoPrendaRayasVerticales;
    private int tiempoPrendaRayasHorizontales;
    private int tiempoRefilado;
    
    public Long getIdLookup() {
        return idLookup;
    }
    public void setIdLookup(Long idLookup) {
        this.idLookup = idLookup;
    }
    public Long getIdTiempoFamiliaPrenda() {
        return idTiempoFamiliaPrenda;
    }
    public void setIdTiempoFamiliaPrenda(Long idTiempoFamiliaPrenda) {
        this.idTiempoFamiliaPrenda = idTiempoFamiliaPrenda;
    }
    public String getFamiliaPrenda() {
        return familiaPrenda;
    }
    public void setFamiliaPrenda(String familiaPrenda) {
        this.familiaPrenda = familiaPrenda;
    }
    public int getTiempoTalla() {
        return tiempoTalla;
    }
    public void setTiempoTalla(int tiempoTalla) {
        this.tiempoTalla = tiempoTalla;
    }
    public int getTiempoPrendaLisa() {
        return tiempoPrendaLisa;
    }
    public void setTiempoPrendaLisa(int tiempoPrendaLisa) {
        this.tiempoPrendaLisa = tiempoPrendaLisa;
    }
    public int getTiempoPrendaCuadros() {
        return tiempoPrendaCuadros;
    }
    public void setTiempoPrendaCuadros(int tiempoPrendaCuadros) {
        this.tiempoPrendaCuadros = tiempoPrendaCuadros;
    }
    public int getTiempoPrendaFantasia() {
        return tiempoPrendaFantasia;
    }
    public void setTiempoPrendaFantasia(int tiempoPrendaFantasia) {
        this.tiempoPrendaFantasia = tiempoPrendaFantasia;
    }
    public int getTiempoPrendaRayasVerticales() {
        return tiempoPrendaRayasVerticales;
    }
    public void setTiempoPrendaRayasVerticales(int tiempoPrendaRayasVerticales) {
        this.tiempoPrendaRayasVerticales = tiempoPrendaRayasVerticales;
    }
    public int getTiempoPrendaRayasHorizontales() {
        return tiempoPrendaRayasHorizontales;
    }
    public void setTiempoPrendaRayasHorizontales(int tiempoPrendaRayasHorizontales) {
        this.tiempoPrendaRayasHorizontales = tiempoPrendaRayasHorizontales;
    }
    public int getTiempoRefilado() {
        return tiempoRefilado;
    }
    public void setTiempoRefilado(int tiempoRefilado) {
        this.tiempoRefilado = tiempoRefilado;
    }
    public TiemposProcesosDto(Long idLookup, Long idTiempoFamiliaPrenda, String familiaPrenda, int tiempoTalla,
            int tiempoPrendaLisa, int tiempoPrendaCuadros, int tiempoPrendaFantasia, int tiempoPrendaRayasVerticales,
            int tiempoPrendaRayasHorizontales, int tiempoRefilado) {
        this.idLookup = idLookup;
        this.idTiempoFamiliaPrenda = idTiempoFamiliaPrenda;
        this.familiaPrenda = familiaPrenda;
        this.tiempoTalla = tiempoTalla;
        this.tiempoPrendaLisa = tiempoPrendaLisa;
        this.tiempoPrendaCuadros = tiempoPrendaCuadros;
        this.tiempoPrendaFantasia = tiempoPrendaFantasia;
        this.tiempoPrendaRayasVerticales = tiempoPrendaRayasVerticales;
        this.tiempoPrendaRayasHorizontales = tiempoPrendaRayasHorizontales;
        this.tiempoRefilado = tiempoRefilado;
    }


}

package com.altima.springboot.app.dto;

public class TiempoCantidadProcesoDto {
    private int cantidadTallas;
    private int tiempoTalla;
    private int tiempoPrendaLisa;
    private int tiempoPrendaCuadros;
    private int tiempoPrendaFantasia;
    private int tiempoPrendaRayasHorizontales;
    private int tiempoPrendaRayasVerticales;
    private int cantidadLiso;
    private int cantidadCuadros;
    private int cantidadFantasia;
    private int cantidadRayasHorizontales;
    private int cantidadRayasVerticales;
    private int cantidadTallasAgrupadas;
    private int tiempoRefilado;
    
    public int getCantidadTallas() {
        return cantidadTallas;
    }
    public void setCantidadTallas(int cantidadTallas) {
        this.cantidadTallas = cantidadTallas;
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
    public int getTiempoPrendaRayasHorizontales() {
        return tiempoPrendaRayasHorizontales;
    }
    public void setTiempoPrendaRayasHorizontales(int tiempoPrendaRayasHorizontales) {
        this.tiempoPrendaRayasHorizontales = tiempoPrendaRayasHorizontales;
    }
    public int getTiempoPrendaRayasVerticales() {
        return tiempoPrendaRayasVerticales;
    }
    public void setTiempoPrendaRayasVerticales(int tiempoPrendaRayasVerticales) {
        this.tiempoPrendaRayasVerticales = tiempoPrendaRayasVerticales;
    }
    public int getCantidadLiso() {
        return cantidadLiso;
    }
    public void setCantidadLiso(int cantidadLiso) {
        this.cantidadLiso = cantidadLiso;
    }
    public int getCantidadCuadros() {
        return cantidadCuadros;
    }
    public void setCantidadCuadros(int cantidadCuadros) {
        this.cantidadCuadros = cantidadCuadros;
    }
    public int getCantidadFantasia() {
        return cantidadFantasia;
    }
    public void setCantidadFantasia(int cantidadFantasia) {
        this.cantidadFantasia = cantidadFantasia;
    }
    public int getCantidadRayasHorizontales() {
        return cantidadRayasHorizontales;
    }
    public void setCantidadRayasHorizontales(int cantidadRayasHorizontales) {
        this.cantidadRayasHorizontales = cantidadRayasHorizontales;
    }
    public int getCantidadRayasVerticales() {
        return cantidadRayasVerticales;
    }
    public void setCantidadRayasVerticales(int cantidadRayasVerticales) {
        this.cantidadRayasVerticales = cantidadRayasVerticales;
    }
    public int getCantidadTallasAgrupadas() {
        return cantidadTallasAgrupadas;
    }
    public void setCantidadTallasAgrupadas(int cantidadTallasAgrupadas) {
        this.cantidadTallasAgrupadas = cantidadTallasAgrupadas;
    }
    public int getTiempoRefilado() {
        return tiempoRefilado;
    }
    public void setTiempoRefilado(int tiempoRefilado) {
        this.tiempoRefilado = tiempoRefilado;
    }
    public TiempoCantidadProcesoDto(int cantidadTallas, int tiempoTalla, int tiempoPrendaLisa, int tiempoPrendaCuadros,
            int tiempoPrendaFantasia, int tiempoPrendaRayasHorizontales, int tiempoPrendaRayasVerticales,
            int cantidadLiso, int cantidadCuadros, int cantidadFantasia, int cantidadRayasHorizontales,
            int cantidadRayasVerticales, int cantidadTallasAgrupadas, int tiempoRefilado) {
        this.cantidadTallas = cantidadTallas;
        this.tiempoTalla = tiempoTalla;
        this.tiempoPrendaLisa = tiempoPrendaLisa;
        this.tiempoPrendaCuadros = tiempoPrendaCuadros;
        this.tiempoPrendaFantasia = tiempoPrendaFantasia;
        this.tiempoPrendaRayasHorizontales = tiempoPrendaRayasHorizontales;
        this.tiempoPrendaRayasVerticales = tiempoPrendaRayasVerticales;
        this.cantidadLiso = cantidadLiso;
        this.cantidadCuadros = cantidadCuadros;
        this.cantidadFantasia = cantidadFantasia;
        this.cantidadRayasHorizontales = cantidadRayasHorizontales;
        this.cantidadRayasVerticales = cantidadRayasVerticales;
        this.cantidadTallasAgrupadas = cantidadTallasAgrupadas;
        this.tiempoRefilado = tiempoRefilado;
    }
    @Override
    public String toString() {
        return "TiempoCantidadProcesoDto [cantidadCuadros=" + cantidadCuadros + ", cantidadFantasia=" + cantidadFantasia
                + ", cantidadLiso=" + cantidadLiso + ", cantidadRayasHorizontales=" + cantidadRayasHorizontales
                + ", cantidadRayasVerticales=" + cantidadRayasVerticales + ", cantidadTallas=" + cantidadTallas
                + ", cantidadTallasAgrupadas=" + cantidadTallasAgrupadas + ", tiempoPrendaCuadros="
                + tiempoPrendaCuadros + ", tiempoPrendaFantasia=" + tiempoPrendaFantasia + ", tiempoPrendaLisa="
                + tiempoPrendaLisa + ", tiempoPrendaRayasHorizontales=" + tiempoPrendaRayasHorizontales
                + ", tiempoPrendaRayasVerticales=" + tiempoPrendaRayasVerticales + ", tiempoRefilado=" + tiempoRefilado
                + ", tiempoTalla=" + tiempoTalla + "]";
    }

}

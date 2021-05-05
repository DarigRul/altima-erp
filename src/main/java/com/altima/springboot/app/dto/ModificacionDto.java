package com.altima.springboot.app.dto;

public class ModificacionDto {

    private Long idModificacion;
    private String especificacion;
    private String pulgadas;
    
    public ModificacionDto(Long idModificacion, String especificacion, String pulgadas) {
        this.idModificacion = idModificacion;
        this.especificacion = especificacion;
        this.pulgadas = pulgadas;
    }
    public Long getIdModificacion() {
        return idModificacion;
    }
    public void setIdModificacion(Long idModificacion) {
        this.idModificacion = idModificacion;
    }
    public String getEspecificacion() {
        return especificacion;
    }
    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }
    public String getPulgadas() {
        return pulgadas;
    }
    public void setPulgadas(String pulgadas) {
        this.pulgadas = pulgadas;
    }
}

package com.altima.springboot.app.dto;

public class TallasPivoteDto {

    private Long idConcentradoTallas;
    private Long idEmpleado;
	private String nombreEmpleado;
	private String familiaPrenda;
	private Long idFamiliaPrenda;
	private String talla;
    

    public TallasPivoteDto(Long idConcentradoTallas, Long idEmpleado, String nombreEmpleado, String familiaPrenda,
            Long idFamiliaPrenda, String talla) {
        this.idConcentradoTallas = idConcentradoTallas;
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.familiaPrenda = familiaPrenda;
        this.idFamiliaPrenda = idFamiliaPrenda;
        this.talla = talla;
    }
    public Long getIdFamiliaPrenda() {
        return idFamiliaPrenda;
    }
    public void setIdFamiliaPrenda(Long idFamiliaPrenda) {
        this.idFamiliaPrenda = idFamiliaPrenda;
    }
    public TallasPivoteDto(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public Long getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    public String getFamiliaPrenda() {
        return familiaPrenda;
    }
    public void setFamiliaPrenda(String familiaPrenda) {
        this.familiaPrenda = familiaPrenda;
    }
    public String getTalla() {
        return talla;
    }
    public void setTalla(String talla) {
        this.talla = talla;
    }
    public Long getIdConcentradoTallas() {
        return idConcentradoTallas;
    }
    public void setIdConcentradoTallas(Long idConcentradoTallas) {
        this.idConcentradoTallas = idConcentradoTallas;
    }

}

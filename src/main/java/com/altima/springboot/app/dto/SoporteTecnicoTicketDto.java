package com.altima.springboot.app.dto;

import java.io.Serializable;

public class SoporteTecnicoTicketDto implements Serializable {

    private Long idTicket;
    private Long idTicketSeguimiento;
    private String idText;
    private String empleado;
    private String area;
    private String prioridad;
    private String categoria;
    private String fechaCreacion;
    private String titulo;
    private String estado;
    private Integer calificacion;
    private String nombreArchivo;
    private String comentario;

    public SoporteTecnicoTicketDto(Long idTicket, String idText, String empleado, String area, String prioridad,
            String categoria, String fechaCreacion, String titulo, String estado, Integer calificacion,
            String nombreArchivo) {
        this.idTicket = idTicket;
        this.idText = idText;
        this.empleado = empleado;
        this.area = area;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.fechaCreacion = fechaCreacion;
        this.titulo = titulo;
        this.estado = estado;
        this.calificacion = calificacion;
        this.nombreArchivo = nombreArchivo;
    }

    public SoporteTecnicoTicketDto(Long idTicketSeguimiento, String empleado, String fechaCreacion, String estado,
            String comentario) {
        this.idTicketSeguimiento = idTicketSeguimiento;
        this.empleado = empleado;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Long getIdTicketSeguimiento() {
        return idTicketSeguimiento;
    }

    public void setIdTicketSeguimiento(Long idTicketSeguimiento) {
        this.idTicketSeguimiento = idTicketSeguimiento;
    }

    @Override
    public String toString() {
        return "SoporteTecnicoTicketDto [area=" + area + ", calificacion=" + calificacion + ", categoria=" + categoria
                + ", comentario=" + comentario + ", empleado=" + empleado + ", estado=" + estado + ", fechaCreacion="
                + fechaCreacion + ", idText=" + idText + ", idTicket=" + idTicket + ", idTicketSeguimiento="
                + idTicketSeguimiento + ", nombreArchivo=" + nombreArchivo + ", prioridad=" + prioridad + ", titulo="
                + titulo + "]";
    }

}

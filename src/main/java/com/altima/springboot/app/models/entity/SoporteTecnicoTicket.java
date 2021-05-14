package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import javax.persistence.Table;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;

import org.hibernate.annotations.GenericGenerator;

@SqlResultSetMapping(name = "ticket_mapping", classes = {
        @ConstructorResult(targetClass = SoporteTecnicoTicketDto.class, columns = {
                @ColumnResult(name = "idTicket", type = Long.class),
                @ColumnResult(name = "idText", type = String.class),
                @ColumnResult(name = "empleado", type = String.class),
                @ColumnResult(name = "area", type = String.class),
                @ColumnResult(name = "prioridad", type = String.class),
                @ColumnResult(name = "categoria", type = String.class),
                @ColumnResult(name = "fechaCreacion", type = String.class),
                @ColumnResult(name = "titulo", type = String.class),
                @ColumnResult(name = "estado", type = String.class),
                @ColumnResult(name = "calificacion", type = Integer.class),
                @ColumnResult(name = "nombreArchivo", type = String.class), }) })

@NamedNativeQueries({
        @NamedNativeQuery(name = "ticket", query = "CALL `alt_pr_soporte_tecnico_ticket`(:idTicket);",resultSetMapping = "ticket_mapping"), })
@Entity
@Table(name = "alt_soporte_tecnico_tickets")
public class SoporteTecnicoTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_ticket")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    private Long idTicket;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "id_empleado")
    private Long idEmpleado;

    @Column(name = "creado_por")
    private String creadoPor;

    @Column(name = "actualizado_por")
    private String actualizadoPor;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "ultima_fecha_modificacion")
    private String ultimaFechaModificacion;

    @Column(name = "prioridad")
    @Enumerated(value = EnumType.STRING)
    private Prioridad prioridad;

    @Column(name = "calificacion")
    private Integer calificacion;

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public enum Prioridad {
        Alta, Normal,
    }
}

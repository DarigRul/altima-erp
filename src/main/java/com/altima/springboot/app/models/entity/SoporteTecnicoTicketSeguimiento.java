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

@SqlResultSetMapping(name = "seguimiento_mapping", classes = {
    @ConstructorResult(targetClass = SoporteTecnicoTicketDto.class, columns = {
            @ColumnResult(name = "idTicketSeguimiento", type = Long.class),
            @ColumnResult(name = "empleado", type = String.class),
            @ColumnResult(name = "fechaCreacion", type = String.class),
            @ColumnResult(name = "estado", type = String.class),
            @ColumnResult(name = "comentario", type = String.class),}) })

@NamedNativeQueries({
    @NamedNativeQuery(name = "seguimiento", query = " CALL `alt_pr_soporte_tecnico_ticket_seguimiento`(:idTicket);",resultSetMapping = "seguimiento_mapping"), })

@Entity
@Table(name = "alt_soporte_tecnico_tickets_seguimiento")
public class SoporteTecnicoTicketSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_ticket_seguimiento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    private Long idTicketSeguimiento;

    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "id_empleado")
    private Long idEmpleado;

    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "fecha_creacion")
    private Estatus fechaCreacion;

    @Column(name = "estatus")
    @Enumerated(value = EnumType.STRING)
    private Estatus estatus;

    public enum Estatus {
        Revisado, Cerrado,
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdTicketSeguimiento() {
        return idTicketSeguimiento;
    }

    public void setIdTicketSeguimiento(Long idTicketSeguimiento) {
        this.idTicketSeguimiento = idTicketSeguimiento;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Estatus getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Estatus fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "SoporteTecnicoTicketSeguimiento [comentarios=" + comentarios + ", estatus=" + estatus
                + ", fechaCreacion=" + fechaCreacion + ", idEmpleado=" + idEmpleado + ", idTicket=" + idTicket
                + ", idTicketSeguimiento=" + idTicketSeguimiento + "]";
    }

}

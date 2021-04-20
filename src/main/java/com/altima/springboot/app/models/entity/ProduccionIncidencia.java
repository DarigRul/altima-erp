package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="alt_produccion_incidencia")
public class ProduccionIncidencia implements Serializable{

    @Id
	@Column(name="id_incidencia")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private Long idIncidencia;

    @Column(name="folio")
    private String folio;

    @Column(name="id_maquilero")
    private String idMaquileroolio;

    @Column(name="id_pedido")
    private String idPedido;

    @Column(name="id_op")
    private String idOp;

    @Column(name="cantidad")
    private String cantidad;

    @Column(name="descuento")
    private String descuento;

    @Column(name="cantidad_descuento")
    private String cantidadDescuento;

    @Column(name="reporte")
	private String reporte;

    @Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	@Column(name="estatus")
	private Integer Estatus;

    public Long getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(Long idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdMaquileroolio() {
        return idMaquileroolio;
    }

    public void setIdMaquileroolio(String idMaquileroolio) {
        this.idMaquileroolio = idMaquileroolio;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdOp() {
        return idOp;
    }

    public void setIdOp(String idOp) {
        this.idOp = idOp;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getCantidadDescuento() {
        return cantidadDescuento;
    }

    public void setCantidadDescuento(String cantidadDescuento) {
        this.cantidadDescuento = cantidadDescuento;
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

    public Integer getEstatus() {
        return Estatus;
    }

    public void setEstatus(Integer estatus) {
        Estatus = estatus;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    
    
}

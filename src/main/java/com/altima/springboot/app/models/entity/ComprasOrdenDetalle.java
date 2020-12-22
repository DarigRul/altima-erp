package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "alt_compras_orden_detalle")
public class ComprasOrdenDetalle implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Id
	@Column(name="id_orden_compras_detalle")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idOrdenComprasDetalle;
	
	@Column(name="id_orden_compras")
	private Long idOrdenCompras;

	@Column(name="id_material")
	private Long idMaterial;
	
	@Column(name="tipo_material")
    private String tipoMaterial;
    
    @Column(name="cantidad")
	private float cantidad;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
    
    @Column(name="creado_por")
    private String creadoPor;

    @Column(name="precio_unitario")
    private float precioUnitario;

    public Long getIdOrdenComprasDetalle() {
        return idOrdenComprasDetalle;
    }

    public void setIdOrdenComprasDetalle(Long idOrdenComprasDetalle) {
        this.idOrdenComprasDetalle = idOrdenComprasDetalle;
    }

    public Long getIdOrdenCompras() {
        return idOrdenCompras;
    }

    public void setIdOrdenCompras(Long idOrdenCompras) {
        this.idOrdenCompras = idOrdenCompras;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
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

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    
}

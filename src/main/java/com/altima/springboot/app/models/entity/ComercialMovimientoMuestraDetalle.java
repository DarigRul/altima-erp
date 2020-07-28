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
@Table(name="alt_comercial_movimiento_muestra_detalle")
public class ComercialMovimientoMuestraDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_movimiento_muestra_detalle")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdMovimientoMuestraDetalle;

	@Column(name="id_detalle_pedido")
	private Long idDetallePedido;
	
	@Column(name="codigo_barras")
	private String codigoBarras;
	
	@Column(name="id_movimiento")
	private Long idMovimiento;
	
	@Column(name="modelo_prenda")
	private String modeloPrenda;
	
	@Column(name="codigo_tela")
	private String codigoTela;
	
	@Column(name="nombre_muestra")
	private String nombreMuestra;
	
	@Column(name="fecha_salida")
	private String fecha_salida;
	
	@Column(name="entregada_por")
	private String entregadaPor;
	
	@Column(name="fecha_devolucion")
	private String fecha_devolucion;
	
	@Column(name="recibida_por")
	private String recibidaPor;
	
	@Column(name="recargos")
	private String recargos;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="estatus")
	private int estatus;

	public Long getIdMovimientoMuestraDetalle() {
		return IdMovimientoMuestraDetalle;
	}

	public void setIdMovimientoMuestraDetalle(Long idMovimientoMuestraDetalle) {
		IdMovimientoMuestraDetalle = idMovimientoMuestraDetalle;
	}

	public Long getIdDetallePedido() {
		return idDetallePedido;
	}

	public void setIdDetallePedido(Long idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}
	
	public String getModeloPrenda() {
		return modeloPrenda;
	}

	public void setModeloPrenda(String modeloPrenda) {
		this.modeloPrenda = modeloPrenda;
	}

	public String getCodigoTela() {
		return codigoTela;
	}

	public void setCodigoTela(String codigoTela) {
		this.codigoTela = codigoTela;
	}

	public String getNombreMuestra() {
		return nombreMuestra;
	}

	public void setNombreMuestra(String nombreMuestra) {
		this.nombreMuestra = nombreMuestra;
	}
	
	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public String getEntregadaPor() {
		return entregadaPor;
	}

	public void setEntregadaPor(String entregadaPor) {
		this.entregadaPor = entregadaPor;
	}

	public String getFecha_devolucion() {
		return fecha_devolucion;
	}

	public void setFecha_devolucion(String fecha_devolucion) {
		this.fecha_devolucion = fecha_devolucion;
	}

	public String getRecibidaPor() {
		return recibidaPor;
	}

	public void setRecibidaPor(String recibidaPor) {
		this.recibidaPor = recibidaPor;
	}

	public String getRecargos() {
		return recargos;
	}

	public void setRecargos(String recargos) {
		this.recargos = recargos;
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

	public String getActualizadoPor() {
		return actualizadoPor;
	}

	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int value) {
		this.estatus = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

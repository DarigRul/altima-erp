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
@Table(name = "alt_comercial_cliente_empleado")
public class ComercialClienteEmpleado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_empleado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idEmpleado;
	
	@Column(name="id_pedido_informacion")
	private Long idPedidoInformacion;
	
	@Column(name="nombre_empleado")
	private String nombre_empleado;
	
	@Column(name="id_cliente_factura")
	private Long idClienteFactura;
	
	@Column(name="id_cliente_sucursal")
	private Long idClienteSucursal;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	public String getNombre_empleado() {
		return nombre_empleado;
	}

	public void setNombre_empleado(String nombre_empleado) {
		this.nombre_empleado = nombre_empleado;
	}

	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	
	
	
	public Long getIdPedidoInformacion() {
		return idPedidoInformacion;
	}

	public void setIdPedidoInformacion(Long idPedidoInformacion) {
		this.idPedidoInformacion = idPedidoInformacion;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdClienteFactura() {
		return idClienteFactura;
	}

	public void setIdClienteFactura(Long idClienteFactura) {
		this.idClienteFactura = idClienteFactura;
	}

	public Long getIdClienteSucursal() {
		return idClienteSucursal;
	}

	public void setIdClienteSucursal(Long idClienteSucursal) {
		this.idClienteSucursal = idClienteSucursal;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}

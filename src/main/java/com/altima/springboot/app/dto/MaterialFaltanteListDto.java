package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MaterialFaltanteListDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long idMaterialFaltante;
	private String idTextPedido;
	private String cliente;
	private String fechaEntrega;
	private String fechaRequisicion;
	private String claveMaterial;
	private String nombreMaterial;
	private Long idProveedor;
	private String nombreProveedor;
	private String idTextProveedor;
	private String color;
	private String tamanio;
	private String cantidad;
	private String fechaPromesa;
	private String fechaOc;
	private String folioOc;
	private String estatus;
	private String estatusComercial;
	private Long idMaterial;
	private String precioUnitario;
	
	public Long getIdMaterialFaltante() {
		return idMaterialFaltante;
	}
	public void setIdMaterialFaltante(Long idMaterialFaltante) {
		this.idMaterialFaltante = idMaterialFaltante;
	}
	public String getIdTextPedido() {
		return idTextPedido;
	}
	public void setIdTextPedido(String idTextPedido) {
		this.idTextPedido = idTextPedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public String getFechaRequisicion() {
		return fechaRequisicion;
	}
	public void setFechaRequisicion(String fechaRequisicion) {
		this.fechaRequisicion = fechaRequisicion;
	}
	public String getClaveMaterial() {
		return claveMaterial;
	}
	public void setClaveMaterial(String claveMaterial) {
		this.claveMaterial = claveMaterial;
	}
	public String getNombreMaterial() {
		return nombreMaterial;
	}
	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}
	public Long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public String getIdTextProveedor() {
		return idTextProveedor;
	}
	public void setIdTextProveedor(String idTextProveedor) {
		this.idTextProveedor = idTextProveedor;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTamanio() {
		return tamanio;
	}
	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getFechaPromesa() {
		return fechaPromesa;
	}
	public void setFechaPromesa(String fechaPromesa) {
		this.fechaPromesa = fechaPromesa;
	}
	public String getFechaOc() {
		return fechaOc;
	}
	public void setFechaOc(String fechaOc) {
		this.fechaOc = fechaOc;
	}
	public String getFolioOc() {
		return folioOc;
	}
	public void setFolioOc(String folioOc) {
		this.folioOc = folioOc;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getEstatusComercial() {
		return estatusComercial;
	}
	public void setEstatusComercial(String estatusComercial) {
		this.estatusComercial = estatusComercial;
	}
	public Long getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}
	public String getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}



}

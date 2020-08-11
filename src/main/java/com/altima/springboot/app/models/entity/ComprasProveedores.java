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
@Table(name="alt_compras_proveedor")
public class ComprasProveedores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_proveedor")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdProveedor;
	
	@Column(name="id_text")
	private String IdText;
	
	@Column(name="nombre_proveedor")
	private String nombreProveedor;
	
	@Column(name="telefono_proveedor")
	private String telefonoProveedor;
	
	@Column(name="calle")
	private String calle;
	
	@Column(name="numero_exterior")
	private String numeroExterior;
	
	@Column(name="numero_interior")
	private String numeroInterior;
	
	@Column(name="colonia")
	private String colonia;
	
	@Column(name="poblacion")
	private String poblacion;
	
	@Column(name="codigo_postal")
	private String codigoPostal;
	
	@Column(name="municipio")
	private String municipio;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="pais")
	private String pais;
	
	@Column(name="clasificacion")
	private String clasificacion;
	
	@Column(name="rfc_proveedor")
	private String rfcProveedor;
	
	@Column(name="curp_proveedor")
	private String curpProveedor;
	
	@Column(name="pagina_web_proveedor")
	private String paginaWebProveedor;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="zona")
	private String zona;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="correo_proveedor")
	private String correoProveedor;
	
	
	
	
	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
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

	public String getCorreoProveedor() {
		return correoProveedor;
	}

	public void setCorreoProveedor(String correoProveedor) {
		this.correoProveedor = correoProveedor;
	}

	public Long getIdProveedor() {
		return IdProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		IdProveedor = idProveedor;
	}

	public String getIdText() {
		return IdText;
	}

	public void setIdText(String idText) {
		IdText = idText;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getTelefonoProveedor() {
		return telefonoProveedor;
	}

	public void setTelefonoProveedor(String telefonoProveedor) {
		this.telefonoProveedor = telefonoProveedor;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getRfcProveedor() {
		return rfcProveedor;
	}

	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}

	public String getCurpProveedor() {
		return curpProveedor;
	}

	public void setCurpProveedor(String curpProveedor) {
		this.curpProveedor = curpProveedor;
	}

	public String getPaginaWebProveedor() {
		return paginaWebProveedor;
	}

	public void setPaginaWebProveedor(String paginaWebProveedor) {
		this.paginaWebProveedor = paginaWebProveedor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

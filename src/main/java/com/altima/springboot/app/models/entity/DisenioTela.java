package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
//algo
@Entity
@Table(name = "alt_disenio_tela")
public class DisenioTela implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_tela")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idTela;
	
	@Column(name="id_familia_composicion")
	private Long idFamiliaComposicion;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name = "id_text_prospecto")
	private String idTextProspecto;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="descripcion_tela")
	private String descripcionTela;
	
	@Column(name="nombre_tela")
	private String nombreTela;
	
	@Column(name="ancho")
	private String ancho;
	
	@Column(name="id_unidad_medida")
	private String idUnidadMedida;
	
	@Column(name="existencia")
	private String existencia;
	
	@Column(name="indicacion")
	private String indicacion;
	
	@Column(name="costo_por_metro")
	private String costoPorMetro;
	
	@Column(name="color")
	private String Color;
	
	@Column(name="codigo_color")
	private String codigoColor;
	
	@Column(name="estampado")
	private String estampado;
	
	@Column(name="id_proveedor")
	private Long idProveedor;
	
	@Column(name="clave_proveedor")
	private String claveProveedor ;
	
	@Column(name="foto")
	private String foto;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="estatus_tela")
	private String estatusTela;
	
	@Column(name="auxiliar_1")
	private String auxiliar1;
	
	@Column(name="auxiliar_2")
	private String auxiliar2;
	
	@Column(name="auxiliar_3")
	private String auxiliar3;
	

	

	public String getAuxiliar1() {
		return auxiliar1;
	}

	public void setAuxiliar1(String auxiliar1) {
		this.auxiliar1 = auxiliar1;
	}

	public String getAuxiliar2() {
		return auxiliar2;
	}

	public void setAuxiliar2(String auxiliar2) {
		this.auxiliar2 = auxiliar2;
	}

	public String getAuxiliar3() {
		return auxiliar3;
	}

	public void setAuxiliar3(String auxiliar3) {
		this.auxiliar3 = auxiliar3;
	}

	public String getIdTextProspecto() {
		return idTextProspecto;
	}

	public void setIdTextProspecto(String idTextProspecto) {
		this.idTextProspecto = idTextProspecto;
	}

	public Long getIdTela() {
		return idTela;
	}

	public void setIdTela(Long idTela) {
		this.idTela = idTela;
	}

	public Long getIdFamiliaComposicion() {
		return idFamiliaComposicion;
	}

	public void setIdFamiliaComposicion(Long idFamiliaComposicion) {
		this.idFamiliaComposicion = idFamiliaComposicion;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
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

	public String getDescripcionTela() {
		return descripcionTela;
	}

	public void setDescripcionTela(String descripcionTela) {
		this.descripcionTela = descripcionTela;
	}

	public String getNombreTela() {
		return nombreTela;
	}

	public void setNombreTela(String nombreTela) {
		this.nombreTela = nombreTela;
	}


	public String getAncho() {
		return ancho;
	}

	public void setAncho(String ancho) {
		this.ancho = ancho;
	}

	public String getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(String idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}


	public String getExistencia() {
		return existencia;
	}

	public void setExistencia(String existencia) {
		this.existencia = existencia;
	}

	public String getIndicacion() {
		return indicacion;
	}

	public void setIndicacion(String indicacion) {
		this.indicacion = indicacion;
	}


	public String getCostoPorMetro() {
		return costoPorMetro;
	}

	public void setCostoPorMetro(String costoPorMetro) {
		this.costoPorMetro = costoPorMetro;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public String getCodigoColor() {
		return codigoColor;
	}

	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}

	public String getEstampado() {
		return estampado;
	}

	public void setEstampado(String estampado) {
		this.estampado = estampado;
	}

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusTela() {
		return estatusTela;
	}

	public void setEstatusTela(String estatusTela) {
		this.estatusTela = estatusTela;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClaveProveedor() {
		return claveProveedor;
	}

	public void setClaveProveedor(String claveProveedor) {
		this.claveProveedor = claveProveedor;
	}


	
}

package com.altima.springboot.app.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "alt_disenio_prenda")
public class DisenioPrenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_prenda")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long idPrenda;

	@Column(name = "id_familia_prenda")
	private Long idFamiliaPrenda;

	@Column(name = "id_text")
	private String idText;
	
	@Column(name = "id_text_prospecto")
	private String idTextProspecto;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

	@Column(name = "fecha_creacion")
	private String fechaCreacion;

	@Column(name = "ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	@Column(name = "numero_prenda")
	private String numeroPrenda;

	@Column(name = "descripcion_prenda")
	private String descripcionPrenda;

	@Column(name = "detalle_prenda")
	private String detallePrenda;

	@Column(name = "nota_especial")
	private String notaEspecial;

	@Column(name = "detalle_confeccion")
	private String detalleConfeccion;

	@Column(name = "consumo_tela")
	private String consumoTela;

	@Column(name = "consumo_forro")
	private String consumoForro;

	@Column(name = "id_ruta")
	private Long idRuta;

	@Column(name = "tipo_largo")
	private String tipoLargo;

	@Column(name = "estatus_recepcion_muestra")
	private String estatusRecepcionMuestra;

	@Column(name = "fecha_recepcion_produccion")
	private String fechaRecepcionProduccion;

	@Column(name = "fecha_devolucion_produccion")
	private String fechaDevolucionProduccion;

	@Column(name = "devolucion")
	private String devolucion;
	
	@Column(name = "categoria")
	private String categoria;

	@Column(name = "estatus")
	private Long estatus;
	
	@Column(name = "prenda_local")
	private String prendaLocal;
	
	@Column(name = "id_genero")
	private String idGenero;

	@Column(name = "mostrar")
	private Boolean mostrar;
	
	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public Long getIdFamiliaPrenda() {
		return idFamiliaPrenda;
	}

	public void setIdFamiliaPrenda(Long idFamiliaPrenda) {
		this.idFamiliaPrenda = idFamiliaPrenda;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public String getIdTextProspecto() {
		return idTextProspecto;
	}

	public void setIdTextProspecto(String idTextProspecto) {
		this.idTextProspecto = idTextProspecto;
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

	public String getNumeroPrenda() {
		return numeroPrenda;
	}

	public void setNumeroPrenda(String numeroPrenda) {
		this.numeroPrenda = numeroPrenda;
	}

	public String getDescripcionPrenda() {
		return descripcionPrenda;
	}

	public void setDescripcionPrenda(String descripcionPrenda) {
		this.descripcionPrenda = descripcionPrenda;
	}

	public String getDetallePrenda() {
		return detallePrenda;
	}

	public void setDetallePrenda(String detallePrenda) {
		this.detallePrenda = detallePrenda;
	}

	public String getNotaEspecial() {
		return notaEspecial;
	}

	public void setNotaEspecial(String notaEspecial) {
		this.notaEspecial = notaEspecial;
	}

	public String getDetalleConfeccion() {
		return detalleConfeccion;
	}

	public void setDetalleConfeccion(String detalleConfeccion) {
		this.detalleConfeccion = detalleConfeccion;
	}

	public String getConsumoTela() {
		return consumoTela;
	}

	public void setConsumoTela(String consumoTela) {
		this.consumoTela = consumoTela;
	}

	public String getConsumoForro() {
		return consumoForro;
	}

	public void setConsumoForro(String consumoForro) {
		this.consumoForro = consumoForro;
	}

	public String getTipoLargo() {
		return tipoLargo;
	}

	public void setTipoLargo(String tipoLargo) {
		this.tipoLargo = tipoLargo;
	}

	public String getEstatusRecepcionMuestra() {
		return estatusRecepcionMuestra;
	}

	public void setEstatusRecepcionMuestra(String estatusRecepcionMuestra) {
		this.estatusRecepcionMuestra = estatusRecepcionMuestra;
	}

	public String getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(String devolucion) {
		this.devolucion = devolucion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Long getEstatus() {
		return estatus;
	}

	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}

	public String getPrendaLocal() {
		return prendaLocal;
	}

	public void setPrendaLocal(String prendaLocal) {
		this.prendaLocal = prendaLocal;
	}

	public String getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(String idGenero) {
		this.idGenero = idGenero;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getMostrar() {
		return mostrar;
	}

	public void setMostrar(Boolean mostrar) {
		this.mostrar = mostrar;
	}

	public String getFechaRecepcionProduccion() {
		return fechaRecepcionProduccion;
	}

	public void setFechaRecepcionProduccion(String fechaRecepcionProduccion) {
		this.fechaRecepcionProduccion = fechaRecepcionProduccion;
	}

	public String getFechaDevolucionProduccion() {
		return fechaDevolucionProduccion;
	}

	public void setFechaDevolucionProduccion(String fechaDevolucionProduccion) {
		this.fechaDevolucionProduccion = fechaDevolucionProduccion;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	
	
	
	
	
	
	
}
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
@Table(name = "alt_comercial_cotizacion_prenda")
public class ComercialCotizacionPrenda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_cotizacion_prenda")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idCotizacionPrenda;
	
	@Column(name="id_cotizacion")
	private Long idCotizacion;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="id_tela")
	private Long idTela;
	
	@Column(name="id_familia_composicion")
	private Long idFamiliaComposicion;
	
	@Column(name="id_familia_prenda")
	private Long idFamiliaPrenda;
	
	@Column(name="cantidad")
	private String cantidad;
	
	@Column(name="coordinado")
	private String coordinado;
	
	@Column(name="precio_unitario_final")
	private String precioUnitarioFinal;
	
	@Column(name="precio_bordado")
	private String precioBordado;
	
	@Column(name="porcentaje_adicional")
	private String porcentajeAdicional;
	
	@Column(name="importe")
	private String importe;
	
	@Column(name="monto_adicional")
	private String montoAdicional;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	public Long getIdCotizacionPrenda() {
		return idCotizacionPrenda;
	}

	public void setIdCotizacionPrenda(Long idCotizacionPrenda) {
		this.idCotizacionPrenda = idCotizacionPrenda;
	}

	public Long getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
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

	public Long getIdFamiliaPrenda() {
		return idFamiliaPrenda;
	}

	public void setIdFamiliaPrenda(Long idFamiliaPrenda) {
		this.idFamiliaPrenda = idFamiliaPrenda;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getCoordinado() {
		return coordinado;
	}

	public void setCoordinado(String coordinado) {
		this.coordinado = coordinado;
	}

	public String getPrecioUnitarioFinal() {
		return precioUnitarioFinal;
	}

	public void setPrecioUnitarioFinal(String precioUnitarioFinal) {
		this.precioUnitarioFinal = precioUnitarioFinal;
	}

	public String getPrecioBordado() {
		return precioBordado;
	}

	public void setPrecioBordado(String precioBordado) {
		this.precioBordado = precioBordado;
	}

	public String getPorcentajeAdicional() {
		return porcentajeAdicional;
	}

	public void setPorcentajeAdicional(String porcentajeAdicional) {
		this.porcentajeAdicional = porcentajeAdicional;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getMontoAdicional() {
		return montoAdicional;
	}

	public void setMontoAdicional(String montoAdicional) {
		this.montoAdicional = montoAdicional;
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

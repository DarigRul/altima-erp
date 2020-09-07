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
@Table(name = "alt_disenio_lista_precio_prenda")
public class DisenioListaPrecioPrenda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_lista_precio_prenda")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idListaPrecioPrenda;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="id_familia_composicion")
	private Long idFamiliaComposicion;
	
	@Column(name="precio_local_nuevo")
	private float precioLocalNuevo;
	
	@Column(name="precio_local_antiguo")
	private float precioLocalAntiguo;
	
	@Column(name="precio_foraneo_nuevo")
	private float precioForaneoNuevo;
	
	@Column(name="precio_foraneo_antiguo")
	private float precioForaneoAntiguo;
	
	@Column(name="costo_maquila")
	private float costoMaquila;

	@Column(name="costo_maquila_muestra")
	private float costoMaquilaMuestra;
	
	@Column(name="precio_muestrario")
	private float precioMuestrario;
	
	@Column(name="precio_linea_express_local_nuevo")
	private float precioLineaExpressLocalNuevo;

	@Column(name="precio_linea_express_local_anterior")
	private float precioLineaExpressLocalAnterior;
	
	@Column(name="precio_linea_express_foraneo_nuevo")
	private float precioLineaExpressForaneoNuevo;

	@Column(name="precio_linea_express_foraneo_anterior")
	private float precioLineaExpressForaneoAnterior;
	
	@Column(name="precio_venta_interna")
	private float precioVentaInterna;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="precio_extra_1")
	private float precioExtra1;
	
	@Column(name="precio_extra_2")
	private float precioExtra2;
	
	@Column(name="precio_extra_3")
	private float precioExtra3;
	
	@Column(name="precio_extra_4")
	private float precioExtra4;
	
	@Column(name="precio_e_commerce")
	private float precioEcommerce;

	
	
	
	public Long getIdFamiliaComposicion() {
		return idFamiliaComposicion;
	}

	public void setIdFamiliaComposicion(Long idFamiliaComposicion) {
		this.idFamiliaComposicion = idFamiliaComposicion;
	}

	public float getPrecioExtra1() {
		return precioExtra1;
	}

	public void setPrecioExtra1(float precioExtra1) {
		this.precioExtra1 = precioExtra1;
	}

	public float getPrecioExtra2() {
		return precioExtra2;
	}

	public void setPrecioExtra2(float precioExtra2) {
		this.precioExtra2 = precioExtra2;
	}

	public float getPrecioExtra3() {
		return precioExtra3;
	}

	public void setPrecioExtra3(float precioExtra3) {
		this.precioExtra3 = precioExtra3;
	}

	public float getPrecioExtra4() {
		return precioExtra4;
	}

	public void setPrecioExtra4(float precioExtra4) {
		this.precioExtra4 = precioExtra4;
	}

	public float getPrecioEcommerce() {
		return precioEcommerce;
	}

	public void setPrecioEcommerce(float precioEcommerce) {
		this.precioEcommerce = precioEcommerce;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdListaPrecioPrenda() {
		return idListaPrecioPrenda;
	}

	public void setIdListaPrecioPrenda(Long idListaPrecioPrenda) {
		this.idListaPrecioPrenda = idListaPrecioPrenda;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public float getPrecioLocalNuevo() {
		return precioLocalNuevo;
	}

	public void setPrecioLocalNuevo(float precioLocalNuevo) {
		this.precioLocalNuevo = precioLocalNuevo;
	}

	public float getPrecioLocalAntiguo() {
		return precioLocalAntiguo;
	}

	public void setPrecioLocalAntiguo(float precioLocalAntiguo) {
		this.precioLocalAntiguo = precioLocalAntiguo;
	}

	public float getPrecioForaneoNuevo() {
		return precioForaneoNuevo;
	}

	public void setPrecioForaneoNuevo(float precioForaneoNuevo) {
		this.precioForaneoNuevo = precioForaneoNuevo;
	}

	public float getPrecioForaneoAntiguo() {
		return precioForaneoAntiguo;
	}

	public void setPrecioForaneoAntiguo(float precioForaneoAntiguo) {
		this.precioForaneoAntiguo = precioForaneoAntiguo;
	}

	public float getPrecioMuestrario() {
		return precioMuestrario;
	}

	public void setPrecioMuestrario(float precioMuestrario) {
		this.precioMuestrario = precioMuestrario;
	}

	public float getPrecioVentaInterna() {
		return precioVentaInterna;
	}

	public void setPrecioVentaInterna(float precioVentaInterna) {
		this.precioVentaInterna = precioVentaInterna;
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

	public float getCostoMaquila() {
		return costoMaquila;
	}

	public void setCostoMaquila(float costoMaquila) {
		this.costoMaquila = costoMaquila;
	}

	public float getCostoMaquilaMuestra() {
		return costoMaquilaMuestra;
	}

	public void setCostoMaquilaMuestra(float costoMaquilaMuestra) {
		this.costoMaquilaMuestra = costoMaquilaMuestra;
	}

	public float getPrecioLineaExpressLocalNuevo() {
		return precioLineaExpressLocalNuevo;
	}

	public void setPrecioLineaExpressLocalNuevo(float precioLineaExpressLocalNuevo) {
		this.precioLineaExpressLocalNuevo = precioLineaExpressLocalNuevo;
	}

	public float getPrecioLineaExpressLocalAnterior() {
		return precioLineaExpressLocalAnterior;
	}

	public void setPrecioLineaExpressLocalAnterior(float precioLineaExpressLocalAnterior) {
		this.precioLineaExpressLocalAnterior = precioLineaExpressLocalAnterior;
	}

	public float getPrecioLineaExpressForaneoNuevo() {
		return precioLineaExpressForaneoNuevo;
	}

	public void setPrecioLineaExpressForaneoNuevo(float precioLineaExpressForaneoNuevo) {
		this.precioLineaExpressForaneoNuevo = precioLineaExpressForaneoNuevo;
	}

	public float getPrecioLineaExpressForaneoAnterior() {
		return precioLineaExpressForaneoAnterior;
	}

	public void setPrecioLineaExpressForaneoAnterior(float precioLineaExpressForaneoAnterior) {
		this.precioLineaExpressForaneoAnterior = precioLineaExpressForaneoAnterior;
	}

	@Override
	public String toString() {
		return "DisenioListaPrecioPrenda [actualizadoPor=" + actualizadoPor + ", costoMaquila=" + costoMaquila
				+ ", costoMaquilaMuestra=" + costoMaquilaMuestra + ", creadoPor=" + creadoPor + ", fechaCreacion="
				+ fechaCreacion + ", idFamiliaComposicion=" + idFamiliaComposicion + ", idListaPrecioPrenda="
				+ idListaPrecioPrenda + ", idPrenda=" + idPrenda + ", precioEcommerce=" + precioEcommerce
				+ ", precioExtra1=" + precioExtra1 + ", precioExtra2=" + precioExtra2 + ", precioExtra3=" + precioExtra3
				+ ", precioExtra4=" + precioExtra4 + ", precioForaneoAntiguo=" + precioForaneoAntiguo
				+ ", precioForaneoNuevo=" + precioForaneoNuevo + ", precioLineaExpressForaneoAnterior="
				+ precioLineaExpressForaneoAnterior + ", precioLineaExpressForaneoNuevo="
				+ precioLineaExpressForaneoNuevo + ", precioLineaExpressLocalAnterior="
				+ precioLineaExpressLocalAnterior + ", precioLineaExpressLocalNuevo=" + precioLineaExpressLocalNuevo
				+ ", precioLocalAntiguo=" + precioLocalAntiguo + ", precioLocalNuevo=" + precioLocalNuevo
				+ ", precioMuestrario=" + precioMuestrario + ", precioVentaInterna=" + precioVentaInterna
				+ ", ultimaFechaModificacion=" + ultimaFechaModificacion + "]";
	}
}

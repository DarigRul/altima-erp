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
@Table(name = "alt_admin_configuracion_pedido")
public class AdminConfiguracionPedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_configuracion_pedido")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idConfiguracionPedido;

	@Column(name="tipo_pedido")
	private Long tipoPedido;
	
	@Column(name="nomenclatura")
	private String nomenclatura;
	
	@Column(name="cantidad_prenda")
	private String cantidadPrenda;
	
	@Column(name="minimo_dias")
	private String minimoDias;
	
	@Column(name="maximo_dias")
	private String maximoDias;
	
	@Column(name="locales")
	private String locales;
	
	@Column(name="foraneo")
	private String foraneo;
	
	@Column(name="dias_bordado")
	private String diasBordado;
	
	@Column(name="stock_true_false")
	private String stockTrueFalse;
	
	@Column(name="minimo_personas")
	private String minimoPersonas;
	
	@Column(name="anticipo_true_false")
	private String anticipoTrueFalse;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="estatus")
	private String estatus;

	public Long getIdConfiguracionPedido() {
		return idConfiguracionPedido;
	}

	public void setIdConfiguracionPedido(Long idConfiguracionPedido) {
		this.idConfiguracionPedido = idConfiguracionPedido;
	}

	
	public Long getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(Long tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getNomenclatura() {
		return nomenclatura;
	}

	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	public String getCantidadPrenda() {
		return cantidadPrenda;
	}

	public void setCantidadPrenda(String cantidadPrenda) {
		this.cantidadPrenda = cantidadPrenda;
	}

	public String getMinimoDias() {
		return minimoDias;
	}

	public void setMinimoDias(String minimoDias) {
		this.minimoDias = minimoDias;
	}

	public String getMaximoDias() {
		return maximoDias;
	}

	public void setMaximoDias(String maximoDias) {
		this.maximoDias = maximoDias;
	}

	public String getLocales() {
		return locales;
	}

	public void setLocales(String locales) {
		this.locales = locales;
	}

	public String getForaneo() {
		return foraneo;
	}

	public void setForaneo(String foraneo) {
		this.foraneo = foraneo;
	}

	public String getDiasBordado() {
		return diasBordado;
	}

	public void setDiasBordado(String diasBordado) {
		this.diasBordado = diasBordado;
	}

	public String getStockTrueFalse() {
		return stockTrueFalse;
	}

	public void setStockTrueFalse(String stockTrueFalse) {
		this.stockTrueFalse = stockTrueFalse;
	}

	public String getMinimoPersonas() {
		return minimoPersonas;
	}

	public void setMinimoPersonas(String minimoPersonas) {
		this.minimoPersonas = minimoPersonas;
	}

	public String getAnticipoTrueFalse() {
		return anticipoTrueFalse;
	}

	public void setAnticipoTrueFalse(String anticipoTrueFalse) {
		this.anticipoTrueFalse = anticipoTrueFalse;
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

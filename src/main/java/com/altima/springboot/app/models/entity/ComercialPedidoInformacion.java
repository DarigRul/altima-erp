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
@Table(name = "alt_comercial_pedido_informacion")
public class ComercialPedidoInformacion implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_pedido_informacion")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idPedidoInformacion;
	
	@Column(name="id_text")
	private String idText;
	
	@Column(name="id_usuario")
	private Long IdUsuario;
	
	@Column(name="id_empresa")
	private Long idEmpresa;
	
	@Column(name="tipo_pedido")
	private String tipoPedido;
	
	@Column(name="id_pedido")
	private Long idPedido;

	@Column(name="validacion")
	private Boolean validacion;

	@Column(name="fecha_explosion_materia_prima")
	private String 	fechaExplosionMateriaPrima;

	@Column(name="estatus_explosion_materia_prima")
	private String estatusExplosionMateriaPrima;
	
	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	@Column(name="fecha_toma_tallas")
	private String fechaTomaTalla;
	
	@Column(name="fecha_anticipo")
	private String fechaAnticipo;
	
	@Column(name="fecha_entrega")
	private String fechaEntrega;
	
	@Column(name="dias_estimados")
	private String diaEstimados;
	
	@Column(name="anticipo")
	private String anticipo;
	
	@Column(name="entrega")
	private String entrega;
	
	@Column(name="saldo")
	private String saldo;
	
	@Column(name="total_pedido")
	private String totalPedido;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_creacion")
	private String ultimaFechaCreacion;
	
		
	@Column(name="precio_usar")
	private String precioUsar;
	
	@Column(name="iva")
	private String iva;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="pago_anticipo")
	private String pagoAnticipo;
	
	@Column(name="adicionales_iva")
	private String adiccionalesIva;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="fecha_cierre")
	private String fechaCierre;
	
	@Column(name="fecha_apartado_telas")
	private String fechaApartadoTelas;
	
	@Column(name="cubre_polvo")
	private String cubrePolvo;
	
	@Column(name="porta_traje")
	private String portaTraje;
	
	@Column(name="otros")
	private String otros;
	
	@Column(name="otros_texto")
	private String otrosTexto;
	

	public String getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Long getIdPedidoInformacion() {
		return idPedidoInformacion;
	}

	public void setIdPedidoInformacion(Long idPedidoInformacion) {
		this.idPedidoInformacion = idPedidoInformacion;
	}
	
	public Long getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getIdText() {
		return idText;
	}

	public void setIdText(String idText) {
		this.idText = idText;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getFechaTomaTalla() {
		return fechaTomaTalla;
	}

	public void setFechaTomaTalla(String fechaTomaTalla) {
		this.fechaTomaTalla = fechaTomaTalla;
	}

	public String getFechaAnticipo() {
		return fechaAnticipo;
	}

	public void setFechaAnticipo(String fechaAnticipo) {
		this.fechaAnticipo = fechaAnticipo;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(String totalPedido) {
		this.totalPedido = totalPedido;
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

	public String getUltimaFechaCreacion() {
		return ultimaFechaCreacion;
	}

	public void setUltimaFechaCreacion(String ultimaFechaCreacion) {
		this.ultimaFechaCreacion = ultimaFechaCreacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDiaEstimados() {
		return diaEstimados;
	}

	public void setDiaEstimados(String diaEstimados) {
		this.diaEstimados = diaEstimados;
	}

	public String getPrecioUsar() {
		return precioUsar;
	}

	public void setPrecioUsar(String precioUsar) {
		this.precioUsar = precioUsar;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getPagoAnticipo() {
		return pagoAnticipo;
	}

	public void setPagoAnticipo(String pagoAnticipo) {
		this.pagoAnticipo = pagoAnticipo;
	}

	public String getAdiccionalesIva() {
		return adiccionalesIva;
	}

	public void setAdiccionalesIva(String adiccionalesIva) {
		this.adiccionalesIva = adiccionalesIva;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Boolean getValidacion() {
		return validacion;
	}

	public void setValidacion(Boolean validacion) {
		this.validacion = validacion;
	}

	public String getFechaApartadoTelas() {
		return fechaApartadoTelas;
	}

	public void setFechaApartadoTelas(String fechaApartadoTelas) {
		this.fechaApartadoTelas = fechaApartadoTelas;
	}

	public String getCubrePolvo() {
		return cubrePolvo;
	}

	public void setCubrePolvo(String cubrePolvo) {
		this.cubrePolvo = cubrePolvo;
	}

	public String getPortaTraje() {
		return portaTraje;
	}

	public void setPortaTraje(String portaTraje) {
		this.portaTraje = portaTraje;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getOtrosTexto() {
		return otrosTexto;
	}

	public void setOtrosTexto(String otrosTexto) {
		this.otrosTexto = otrosTexto;
	}

	public String getFechaExplosionMateriaPrima() {
		return fechaExplosionMateriaPrima;
	}

	public void setFechaExplosionMateriaPrima(String fechaExplosionMateriaPrima) {
		this.fechaExplosionMateriaPrima = fechaExplosionMateriaPrima;
	}

	public String getEstatusExplosionMateriaPrima() {
		return estatusExplosionMateriaPrima;
	}

	public void setEstatusExplosionMateriaPrima(String estatusExplosionMateriaPrima) {
		this.estatusExplosionMateriaPrima = estatusExplosionMateriaPrima;
	}

	
}

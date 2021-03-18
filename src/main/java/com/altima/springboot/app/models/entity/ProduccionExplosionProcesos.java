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
@Table(name = "alt_produccion_explosion_procesos")
public class ProduccionExplosionProcesos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_explosion_procesos")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long idExplosionProcesos;

	@Column(name = "id_pedido")
	private Long idPedido;

	@Column(name = "coordinado")
	private Long idCoordinado;

	@Column(name = "programa")
	private String programa;

	@Column(name = "clave_proceso")
	private Long claveProceso;

	@Column(name = "fecha_inicio")
	private String fechaInicio;

	@Column(name = "fecha_fin")
	private String fechaFin;

	@Column(name = "quien_realizo")
	private String quienRealizo;

	@Column(name = "comentarios")
	private String comentarios;

	@Column(name = "estatus_proceso")
	private int estatusProceso;

	@Column(name = "tiempo_corte")
	private double tiempoCorte;

	@Column(name = "secuencia")
	private int secuencia;

	@Column(name = "clave_prenda")
	private Long clavePrenda;

	@Column(name = "fecha_entrega")
	private String fechaEntrega;

	@Column(name = "fecha_real_recurrente")
	private String fechaRealRecurrente;

	@Column(name = "fecha_explosion")
	private String fechaExplosion;

	@Column(name = "estatus")
	private String estatus;

	@Column(name = "fecha_proceso")
	private String fechaProceso;
	@Column(name = "tiempo_proceso")
	private String tiempoProceso;

	@Column(name = "tiempo_general")
	private String tiempoGeneral;
	
	@Column(name = "secuencia_proceso")
	private String secuenciaProceso;

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getFechaExplosion() {
		return fechaExplosion;
	}

	public void setFechaExplosion(String fechaExplosion) {
		this.fechaExplosion = fechaExplosion;
	}

	public Long getIdExplosionProcesos() {
		return idExplosionProcesos;
	}

	public void setIdExplosionProcesos(Long idExplosionProcesos) {
		this.idExplosionProcesos = idExplosionProcesos;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdCoordinado() {
		return idCoordinado;
	}

	public void setIdCoordinado(Long idCoordinado) {
		this.idCoordinado = idCoordinado;
	}

	public Long getClaveProceso() {
		return claveProceso;
	}

	public void setClaveProceso(Long claveProceso) {
		this.claveProceso = claveProceso;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getQuienRealizo() {
		return quienRealizo;
	}

	public void setQuienRealizo(String quienRealizo) {
		this.quienRealizo = quienRealizo;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public int getEstatusProceso() {
		return estatusProceso;
	}

	public void setEstatusProceso(int estatusProceso) {
		this.estatusProceso = estatusProceso;
	}

	public double getTiempoCorte() {
		return tiempoCorte;
	}

	public void setTiempoCorte(double tiempoCorte) {
		this.tiempoCorte = tiempoCorte;
	}

	public int getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	public Long getClavePrenda() {
		return clavePrenda;
	}

	public void setClavePrenda(Long l) {
		this.clavePrenda = l;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getFechaRealRecurrente() {
		return fechaRealRecurrente;
	}

	public void setFechaRealRecurrente(String fechaRealRecurrente) {
		this.fechaRealRecurrente = fechaRealRecurrente;
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

	public String getFechaProceso() {
		return fechaProceso;
	}

	public void setFechaProceso(String fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getTiempoProceso() {
		return tiempoProceso;
	}

	public void setTiempoProceso(String tiempoProceso) {
		this.tiempoProceso = tiempoProceso;
	}

	public String getSecuenciaProceso() {
		return secuenciaProceso;
	}

	public void setSecuenciaProceso(String secuenciaProceso) {
		this.secuenciaProceso = secuenciaProceso;
	}

	public String getTiempoGeneral() {
		return tiempoGeneral;
	}

	public void setTiempoGeneral(String tiempoGeneral) {
		this.tiempoGeneral = tiempoGeneral;
	}

}

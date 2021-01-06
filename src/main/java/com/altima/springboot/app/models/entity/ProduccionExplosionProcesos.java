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
	@Column(name="id_explosion_procesos")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idExplosionProcesos;
	
	@Column(name="id_pedido")
	private Long idPedido;
	
	@Column(name="programa")
	private String programa;

	@Column(name="consecutivo")
	private Long consecutivo;
	 
	@Column(name="clave_proceso")
	private Long claveProceso;
	
	@Column(name="consumo_tela")
	private double consumoTela;
	
	@Column(name="fecha_planeada")
	private String fechaPlaneada;

	@Column(name="fecha_real")
	private String fechaReal;
	
	@Column(name="quien_realizo")
	private String quienRealizo;
	
	@Column(name="comentarios")
	private String comentarios;
	
	@Column(name="estatus_proceso")
	private int estatusProceso;
	
	@Column(name="tiempo_proceso")
	private double tiempoProceso;
	
	@Column(name="secuencia")
	private int secuencia;
	
	@Column(name="clave_prenda")
	private Long clavePrenda;
	
	@Column(name="fecha_planeada_recurrente")
	private String fechaPlaneadaRecurrente;
	
	@Column(name="fecha_real_recurrente")
	private String fechaRealRecurrente;
	
	@Column(name="fecha_explosion")
	private String fechaExplosion;

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

	public Long getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Long getClaveProceso() {
		return claveProceso;
	}

	public void setClaveProceso(Long claveProceso) {
		this.claveProceso = claveProceso;
	}

	public double getConsumoTela() {
		return consumoTela;
	}

	public void setConsumoTela(double consumoTela) {
		this.consumoTela = consumoTela;
	}

	public String getFechaPlaneada() {
		return fechaPlaneada;
	}

	public void setFechaPlaneada(String fechaPlaneada) {
		this.fechaPlaneada = fechaPlaneada;
	}

	public String getFechaReal() {
		return fechaReal;
	}

	public void setFechaReal(String fechaReal) {
		this.fechaReal = fechaReal;
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

	public double getTiempoProceso() {
		return tiempoProceso;
	}

	public void setTiempoProceso(double tiempoProceso) {
		this.tiempoProceso = tiempoProceso;
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

	public String getFechaPlaneadaRecurrente() {
		return fechaPlaneadaRecurrente;
	}

	public void setFechaPlaneadaRecurrente(String fechaPlaneadaRecurrente) {
		this.fechaPlaneadaRecurrente = fechaPlaneadaRecurrente;
	}

	public String getFechaRealRecurrente() {
		return fechaRealRecurrente;
	}

	public void setFechaRealRecurrente(String fechaRealRecurrente) {
		this.fechaRealRecurrente = fechaRealRecurrente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

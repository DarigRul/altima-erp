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
@Table(name = "alt_maquila_asignacion_tickets")
public class MaquilaAsignacionTickets implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_asignacion_ticket")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idAsignacionTicket;
	
	@Column(name="id_control_pedido_embultado")
	private Long idControlPedidoEmbultado;
	
	@Column(name="id_control_pedido")
	private Long idControlPedido;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="pedido")
	private String pedido;
	
	@Column(name="modelo")
	private String modelo;
	
	@Column(name="bulto")
	private String bulto;
	
	@Column(name="cantidad_prenda_bulto")
	private String cantidadPredaBulto;
	
	@Column(name="familia")
	private String familia;
	
	@Column(name="operacion")
	private String operacion;
	
	@Column(name="sam")
	private String sam;
	
	@Column(name="hrs")
	private String hrs;
	
	@Column(name="turno")
	private String turno;
	
	@Column(name="tiempo_estimado")
	private String tiempoEstimado;
	
	@Column(name="ticket")
	private String ticket;
	
	@Column(name="operario")
	private String operario;
	
	@Column(name="fecha_asignacion")
	private String fechaAsignacion;

	public Long getIdAsignacionTicket() {
		return idAsignacionTicket;
	}

	public void setIdAsignacionTicket(Long idAsignacionTicket) {
		this.idAsignacionTicket = idAsignacionTicket;
	}

	public Long getIdControlPedidoEmbultado() {
		return idControlPedidoEmbultado;
	}

	public void setIdControlPedidoEmbultado(Long idControlPedidoEmbultado) {
		this.idControlPedidoEmbultado = idControlPedidoEmbultado;
	}

	public Long getIdControlPedido() {
		return idControlPedido;
	}

	public void setIdControlPedido(Long idControlPedido) {
		this.idControlPedido = idControlPedido;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getBulto() {
		return bulto;
	}

	public void setBulto(String bulto) {
		this.bulto = bulto;
	}

	public String getCantidadPredaBulto() {
		return cantidadPredaBulto;
	}

	public void setCantidadPredaBulto(String cantidadPredaBulto) {
		this.cantidadPredaBulto = cantidadPredaBulto;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getSam() {
		return sam;
	}

	public void setSam(String sam) {
		this.sam = sam;
	}

	public String getHrs() {
		return hrs;
	}

	public void setHrs(String hrs) {
		this.hrs = hrs;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(String tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getOperario() {
		return operario;
	}

	public void setOperario(String operario) {
		this.operario = operario;
	}

	public String getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	


}

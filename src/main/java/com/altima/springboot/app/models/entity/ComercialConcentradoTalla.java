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
@Table(name = "alt_comercial_concentrado_tallas")
public class ComercialConcentradoTalla implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long Id;

	@Column(name = "id_pedido")
	private Long IdPedido;

	@Column(name = "id_empleado_pedido")
	private String IdEmpleadoPedido;

	@Column(name = "id_prenda_cliente")
	private String IdPrendaCliente;

	@Column(name = "id_talla")
	private Integer IdTalla;

	@Column(name = "id_largo")
	private Integer IdLargo;

	@Column(name = "pulgadas")
	private String Pulgadas;

	@Column(name = "especificacion")
	private String Especificacion;

	@Column(name = "id_familia_prenda")
	private String IdFamiliaPrenda;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getIdPedido() {
		return IdPedido;
	}

	public void setIdPedido(Long idPedido) {
		IdPedido = idPedido;
	}

	public String getIdEmpleadoPedido() {
		return IdEmpleadoPedido;
	}

	public void setIdEmpleadoPedido(String idEmpleadoPedido) {
		IdEmpleadoPedido = idEmpleadoPedido;
	}

	public String getIdPrendaCliente() {
		return IdPrendaCliente;
	}

	public void setIdPrendaCliente(String idPrendaCliente) {
		IdPrendaCliente = idPrendaCliente;
	}

	public Integer getIdTalla() {
		return IdTalla;
	}

	public void setIdTalla(Integer idTalla) {
		IdTalla = idTalla;
	}

	public Integer getIdLargo() {
		return IdLargo;
	}

	public void setIdLargo(Integer idLargo) {
		IdLargo = idLargo;
	}

	public String getPulgadas() {
		return Pulgadas;
	}

	public void setPulgadas(String pulgadas) {
		Pulgadas = pulgadas;
	}

	public String getEspecificacion() {
		return Especificacion;
	}

	public void setEspecificacion(String especificacion) {
		Especificacion = especificacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdFamiliaPrenda() {
		return IdFamiliaPrenda;
	}

	public void setIdFamiliaPrenda(String idFamiliaPrenda) {
		IdFamiliaPrenda = idFamiliaPrenda;
	}

}

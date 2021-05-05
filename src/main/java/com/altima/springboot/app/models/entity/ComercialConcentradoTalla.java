package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import javax.persistence.Table;

import com.altima.springboot.app.dto.TallasPivoteDto;

import org.hibernate.annotations.GenericGenerator;

@SqlResultSetMapping(name = "tallas_pivote_mapping", classes = {
		@ConstructorResult(targetClass = TallasPivoteDto.class, columns = {
				@ColumnResult(name = "idConcentradoTallas", type = Long.class),
				@ColumnResult(name = "idEmpleado", type = Long.class),
				@ColumnResult(name = "nombreEmpleado", type = String.class),
				@ColumnResult(name = "familiaPrenda", type = String.class),
				@ColumnResult(name = "idFamiliaPrenda", type = Long.class),
				@ColumnResult(name = "talla", type = String.class), }) })

@NamedNativeQueries({
		@NamedNativeQuery(name = "tallas_pivote", query = "select idConcentradoTallas,idEmpleado,nombreEmpleado,familiaPrenda,idFamiliaPrenda,talla from alt_view_tallas_pivote where idPedido=:idPedido", resultSetMapping = "tallas_pivote_mapping"),
		@NamedNativeQuery(name = "idEmpleado_pivote", query = "select idConcentradoTallas,idEmpleado,nombreEmpleado,familiaPrenda,idFamiliaPrenda,talla from alt_view_tallas_pivote where idEmpleado=:idEmpleado", resultSetMapping = "tallas_pivote_mapping"), })
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
	private Long id;

	@Column(name = "id_pedido")
	private Long idPedido;

	@Column(name = "id_empleado_pedido")
	private String idEmpleadoPedido;

	@Column(name = "id_talla")
	private Integer idTalla;

	@Column(name = "id_largo")
	private Integer idLargo;

	@Column(name = "id_familia_prenda")
	private String idFamiliaPrenda;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "actualizado_por")
	private String actualizadoPor;

	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "fecha_creacion")
	private String fechaCreacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getIdEmpleadoPedido() {
		return idEmpleadoPedido;
	}

	public void setIdEmpleadoPedido(String idEmpleadoPedido) {
		this.idEmpleadoPedido = idEmpleadoPedido;
	}

	public Integer getIdTalla() {
		return idTalla;
	}

	public void setIdTalla(Integer idTalla) {
		this.idTalla = idTalla;
	}

	public Integer getIdLargo() {
		return idLargo;
	}

	public void setIdLargo(Integer idLargo) {
		this.idLargo = idLargo;
	}

	public String getIdFamiliaPrenda() {
		return idFamiliaPrenda;
	}

	public void setIdFamiliaPrenda(String idFamiliaPrenda) {
		this.idFamiliaPrenda = idFamiliaPrenda;
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

	public String getUltimaFechaModificacion() {
		return ultimaFechaModificacion;
	}

	public void setUltimaFechaModificacion(String ultimaFechaModificacion) {
		this.ultimaFechaModificacion = ultimaFechaModificacion;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "ComercialConcentradoTalla [actualizadoPor=" + actualizadoPor + ", creadoPor=" + creadoPor
				+ ", fechaCreacion=" + fechaCreacion + ", id=" + id + ", idEmpleadoPedido=" + idEmpleadoPedido
				+ ", idFamiliaPrenda=" + idFamiliaPrenda + ", idLargo=" + idLargo + ", idPedido=" + idPedido
				+ ", idTalla=" + idTalla + ", ultimaFechaModificacion=" + ultimaFechaModificacion + "]";
	}



}

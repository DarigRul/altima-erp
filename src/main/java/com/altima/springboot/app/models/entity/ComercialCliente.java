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
@Table(name = "alt_comercial_cliente")
public class ComercialCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_cliente")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long idCliente;

	@Column(name = "id_usuario")
	private Long IdUsuario;

	@Column(name = "id_text")
	private String CidText;

	@Column(name = "creado_por")
	private String CcreadoPor;

	@Column(name = "actualizado_por")
	private String CactualizadoPor;

	@Column(name = "fecha_creacion")
	private String CfechaCreacion;

	@Column(name = "ultima_fecha_modificacion")
	private String CultimaFechaModificacion;

	@Column(name = "apellido_paterno")
	private String ApellidoPaterno;

	@Column(name = "apellido_materno")
	private String ApellidoMaterno;

	@Column(name = "nombre")
	private String Nombre;

	@Column(name = "razon_social")
	private String RazonSocial;

	@Column(name = "correo")
	private String Correo;

	@Column(name = "telefono")
	private String Telefono;

	@Column(name = "rfc")
	private String Rfc;

	@Column(name = "pagina_web")
	private String PaginaWeb;

	@Column(name = "observacion")
	private String Observacion;

	@Column(name = "tipo_cliente")
	private String TipoCliente;

	@Column(name = "id_direccion")
	private Long IdDireccion;

	@Column(name = "estatus")
	private Integer estatusC;

	@Column(name = "imagen")
	private String imagen;

	@Column(name = "puesto_contacto")
	private String puestoContacto;

	@Column(name = "nombre_contacto")
	private String nombreContacto;

	@Column(name = "estatus_cliente")
	private Integer estatusCliente;
	
	@Column(name = "foraneo")
	private Integer foraneo;
	
	

	public Integer getForaneo() {
		return foraneo;
	}

	public void setForaneo(Integer foraneo) {
		this.foraneo = foraneo;
	}

	public Long getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getPuestoContacto() {
		return puestoContacto;
	}

	public void setPuestoContacto(String puestoContacto) {
		this.puestoContacto = puestoContacto;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public Integer getEstatusCliente() {
		return estatusCliente;
	}

	public void setEstatusCliente(Integer estatusCliente) {
		this.estatusCliente = estatusCliente;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getCidText() {
		return CidText;
	}

	public void setCidText(String cidText) {
		CidText = cidText;
	}

	public String getCcreadoPor() {
		return CcreadoPor;
	}

	public void setCcreadoPor(String ccreadoPor) {
		CcreadoPor = ccreadoPor;
	}

	public String getCactualizadoPor() {
		return CactualizadoPor;
	}

	public void setCactualizadoPor(String cactualizadoPor) {
		CactualizadoPor = cactualizadoPor;
	}

	public String getCfechaCreacion() {
		return CfechaCreacion;
	}

	public void setCfechaCreacion(String cfechaCreacion) {
		CfechaCreacion = cfechaCreacion;
	}

	public String getCultimaFechaModificacion() {
		return CultimaFechaModificacion;
	}

	public void setCultimaFechaModificacion(String cultimaFechaModificacion) {
		CultimaFechaModificacion = cultimaFechaModificacion;
	}

	public String getApellidoPaterno() {
		return ApellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		ApellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return ApellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		ApellidoMaterno = apellidoMaterno;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getRazonSocial() {
		return RazonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}

	public String getCorreo() {
		return Correo;
	}

	public void setCorreo(String correo) {
		Correo = correo;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getRfc() {
		return Rfc;
	}

	public void setRfc(String rfc) {
		Rfc = rfc;
	}

	public String getPaginaWeb() {
		return PaginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		PaginaWeb = paginaWeb;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public String getTipoCliente() {
		return TipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		TipoCliente = tipoCliente;
	}

	public Long getIdDireccion() {
		return IdDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		IdDireccion = idDireccion;
	}

	public Integer getEstatusC() {
		return estatusC;
	}

	public void setEstatusC(Integer estatus) {
		this.estatusC = estatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
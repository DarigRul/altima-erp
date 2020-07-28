package com.altima.springboot.app.models.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="alt_comercial_cliente_sucursal")
public class ComercialClienteSucursal implements Serializable{
	
	private static final long  serialVersionUID=1L;
	
	@Id
	@Column(name="id_cliente_sucursal")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long IdClienteSucursal;
	
	@Column(name="id_text")
	private String SIdText;
	
	@Column(name="id_cliente")
	private String IdCliente;
	
	@Column(name="id_direccion")
	private Long IdDireccion;
	
	@Column(name="no_sucursal")
	private String NoSucursal;
	
	@Column(name="nombre_sucursal")
	private String NombreSucursal;
	
	@Column(name="telefono_sucursal")
	private String TelefonoSucursal;
	
	@Column(name="rfc_sucursal")
	private String RfcSucursal;
	
	@Column(name="contacto_sucursal") 
	private String ContactoSucursal;
	
	@Column(name="puesto_contacto")
	private String puestoContacto;
	
	@Column(name="creado_por")
	private String SCreadoPor;

	@Column(name="actualizado_por")
	private String SActualizadoPor;
	
	@Column(name="fecha_creacion")
	private String SFechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String SUltimaFechaModificacion;
	
	@Column(name="observacion")
	private String SObservacion;
	
	@Column(name="estatus")
	private String Sestatus;
	
	@Column(name="id_agente")
	private Long IdAgente;
	
	
	
	
	public String getPuestoContacto() {
		return puestoContacto;
	}



	public void setPuestoContacto(String puestoContacto) {
		this.puestoContacto = puestoContacto;
	}




	public String getSestatus() {
		return Sestatus;
	}



	public void setSestatus(String sestatus) {
		Sestatus = sestatus;
	}



	public Long getIdClienteSucursal() {
		return IdClienteSucursal;
	}



	public void setIdClienteSucursal(Long idClienteSucursal) {
		IdClienteSucursal = idClienteSucursal;
	}



	public String getSIdText() {
		return SIdText;
	}



	public void setSIdText(String sIdText) {
		SIdText = sIdText;
	}



	public String getIdCliente() {
		return IdCliente;
	}



	public void setIdCliente(String idCliente) {
		IdCliente = idCliente;
	}



	public String getNoSucursal() {
		return NoSucursal;
	}



	public void setNoSucursal(String noSucursal) {
		NoSucursal = noSucursal;
	}



	public String getNombreSucursal() {
		return NombreSucursal;
	}



	public void setNombreSucursal(String nombreSucursal) {
		NombreSucursal = nombreSucursal;
	}



	public String getTelefonoSucursal() {
		return TelefonoSucursal;
	}



	public void setTelefonoSucursal(String telefonoSucursal) {
		TelefonoSucursal = telefonoSucursal;
	}



	public String getRfcSucursal() {
		return RfcSucursal;
	}



	public void setRfcSucursal(String rfcSucursal) {
		RfcSucursal = rfcSucursal;
	}



	public String getContactoSucursal() {
		return ContactoSucursal;
	}



	public void setContactoSucursal(String contactoSucursal) {
		ContactoSucursal = contactoSucursal;
	}



	public String getSCreadoPor() {
		return SCreadoPor;
	}



	public void setSCreadoPor(String sCreadoPor) {
		SCreadoPor = sCreadoPor;
	}



	public String getSActualizadoPor() {
		return SActualizadoPor;
	}



	public void setSActualizadoPor(String sActualizadoPor) {
		SActualizadoPor = sActualizadoPor;
	}



	public String getSFechaCreacion() {
		return SFechaCreacion;
	}



	public void setSFechaCreacion(String sFechaCreacion) {
		SFechaCreacion = sFechaCreacion;
	}



	public String getSUltimaFechaModificacion() {
		return SUltimaFechaModificacion;
	}



	public void setSUltimaFechaModificacion(String sUltimaFechaModificacion) {
		SUltimaFechaModificacion = sUltimaFechaModificacion;
	}



	public String getSObservacion() {
		return SObservacion;
	}



	public void setSObservacion(String sObservacion) {
		SObservacion = sObservacion;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Long getIdDireccion() {
		return IdDireccion;
	}



	public void setIdDireccion(Long idDireccion) {
		IdDireccion = idDireccion;
	}



	public Long getIdAgente() {
		return IdAgente;
	}



	public void setIdAgente(Long idAgente) {
		IdAgente = idAgente;
	}

	

	
}

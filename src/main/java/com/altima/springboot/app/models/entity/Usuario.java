package com.altima.springboot.app.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "alt_hr_usuario")

public class Usuario  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	@Column(name="id_usuario")
	private Long idUsuario;
	
	
	@Column(name="id_text")
	
	private String idText;
	
	@Column(name="nombre_usuario")
	private String nombreUsuario;
	
	@Column(name="id_empleado")
	private Long idEmpleado;
	
	@Column(name="creado_por")
	
	private String creadoPor;
	
	@Column(name="actualizado_por")
	
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	
	private String ultimaFechaModificacion;
	
	@Column(name="contraseña")
	private String contrasena;
	
	@Column(name="estatus")
	private String estatus;
	
	@Transient 
	private String confirmPassword;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "alt_hr_usuario_rol",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn(name = "id_rol") })
    private Set<Rol> roles = new HashSet<>();
	
	
	

	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdText() {
		return idText;
	}
	public void setIdText(String idText) {
		this.idText = idText;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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
	public String getContraseña() {
		return contrasena;
	}
	public void setContraseña(String contraseña) {
		this.contrasena = contraseña;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Set<Rol> getRoles() {
		return roles;
	}
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public void removeRol(Set<Rol> roles){
		roles.removeAll(this.roles);
	}
	
}

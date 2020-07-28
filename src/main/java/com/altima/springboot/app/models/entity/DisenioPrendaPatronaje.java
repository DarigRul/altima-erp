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
@Table(name = "alt_disenio_prenda_patronaje")
public class DisenioPrendaPatronaje implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id_prenda_patronaje")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idPrendaPatronaje;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="id_patronaje")
	private String idPatronaje;
	
	@Column(name="cantidad_tela")
	private String cantidadTela;
	
	@Column(name="cantidad_forro")
	private String cantidadForro;
	
	@Column(name="cantidad_entretela")
	private String cantidadEntretela;
	
	@Column(name="cantidad_tela_secundaria")
	private String cantidadTelaSecundaria;
	
	@Column(name="cantidad_forro_secundario")
	private String cantidadForroSecundaria;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	

	public String getCantidadTelaSecundaria() {
		return cantidadTelaSecundaria;
	}

	public void setCantidadTelaSecundaria(String cantidadTelaSecundaria) {
		this.cantidadTelaSecundaria = cantidadTelaSecundaria;
	}

	public String getCantidadForroSecundaria() {
		return cantidadForroSecundaria;
	}

	public void setCantidadForroSecundaria(String cantidadForroSecundaria) {
		this.cantidadForroSecundaria = cantidadForroSecundaria;
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

	public Long getIdPrendaPatronaje() {
		return idPrendaPatronaje;
	}

	public void setIdPrendaPatronaje(Long idPrendaPatronaje) {
		this.idPrendaPatronaje = idPrendaPatronaje;
	}

	public Long getIdPrenda() {
		return idPrenda;
	}

	public void setIdPrenda(Long idPrenda) {
		this.idPrenda = idPrenda;
	}

	public String getIdPatronaje() {
		return idPatronaje;
	}

	public void setIdPatronaje(String idPatronaje) {
		this.idPatronaje = idPatronaje;
	}

	public String getCantidadTela() {
		return cantidadTela;
	}

	public void setCantidadTela(String cantidadTela) {
		this.cantidadTela = cantidadTela;
	}

	public String getCantidadForro() {
		return cantidadForro;
	}

	public void setCantidadForro(String cantidadForro) {
		this.cantidadForro = cantidadForro;
	}

	public String getCantidadEntretela() {
		return cantidadEntretela;
	}

	public void setCantidadEntretela(String cantidadEntretela) {
		this.cantidadEntretela = cantidadEntretela;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidadEntretela == null) ? 0 : cantidadEntretela.hashCode());
		result = prime * result + ((cantidadForro == null) ? 0 : cantidadForro.hashCode());
		result = prime * result + ((cantidadTela == null) ? 0 : cantidadTela.hashCode());
		result = prime * result + ((idPatronaje == null) ? 0 : idPatronaje.hashCode());
		result = prime * result + ((idPrenda == null) ? 0 : idPrenda.hashCode());
		result = prime * result + ((idPrendaPatronaje == null) ? 0 : idPrendaPatronaje.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisenioPrendaPatronaje other = (DisenioPrendaPatronaje) obj;
		if (cantidadEntretela == null) {
			if (other.cantidadEntretela != null)
				return false;
		} else if (!cantidadEntretela.equals(other.cantidadEntretela))
			return false;
		if (cantidadForro == null) {
			if (other.cantidadForro != null)
				return false;
		} else if (!cantidadForro.equals(other.cantidadForro))
			return false;
		if (cantidadTela == null) {
			if (other.cantidadTela != null)
				return false;
		} else if (!cantidadTela.equals(other.cantidadTela))
			return false;
		if (idPatronaje == null) {
			if (other.idPatronaje != null)
				return false;
		} else if (!idPatronaje.equals(other.idPatronaje))
			return false;
		if (idPrenda == null) {
			if (other.idPrenda != null)
				return false;
		} else if (!idPrenda.equals(other.idPrenda))
			return false;
		if (idPrendaPatronaje == null) {
			if (other.idPrendaPatronaje != null)
				return false;
		} else if (!idPrendaPatronaje.equals(other.idPrendaPatronaje))
			return false;
		return true;
	}
}

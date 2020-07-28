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
@Table(name = "alt_disenio_material")
public class DisenioMaterial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_material")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idMaterial;
	
	@Column(name="id_proceso")
	
	private Long idProceso;

	@Column(name="id_text")
	
	private String idText;
	
	@Column(name="creado_por")

	private String creadoPor;
	
	@Column(name="actualizado_por")
	
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	
	private String ultimaFechaModificacion;

	@Column(name="descripcion_material")

	private String descripcionMaterial;
	
	@Column(name="id_unidad_medida")

	private String idUnidadMedida;
	
	@Column(name="modelo")
	
	private String modelo;
	
	@Column(name="precio_unitario")

	private String precioUnitario;
	@Column(name="incluir_en_patronaje")
	
	private String incluirEnPatronaje;
	@Column(name="tamanio")
	
	private String tamanio;
	@Column(name="id_marca")
	
	private String idMarca;
    @Column(name="id_clasificacion")
	
	private String  idClasificacion ;
    @Column(name="nombre_material")
	
   	private String  nombreMaterial ;
	@Column(name="modelo_proveedor")
	
	private String modeloProveedor;
	@Column(name="id_almacen")

	private Long idAlmacen;
	
	@Column(name="estatus")

	private String estatus;
	
	@Column(name="estatus_material")

	private String estatusMaterial;
	
	@Column(name = "id_text_prospecto")
	private String idTextProspecto;
	
	@Column(name="id_tipo_material")
	private Long idTipoMaterial;
	
	@Column(name="id_color")
	private Long idColor;
	
	@Column(name="calidad")
	private String calidad;
	
	@Column(name="imagen")
	private String imagen;
	
	@Column(name="proveedor")
	private String proveedor;

	
	
	
	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

	public String getEstatusMaterial() {
		return estatusMaterial;
	}

	public void setEstatusMaterial(String estatusMaterial) {
		this.estatusMaterial = estatusMaterial;
	}

	public String getIdTextProspecto() {
		return idTextProspecto;
	}

	public void setIdTextProspecto(String idTextProspecto) {
		this.idTextProspecto = idTextProspecto;
	}

	public Long getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Long idMaterial) {
		this.idMaterial = idMaterial;
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdText() {
		return idText;
	}
	
	public void setIdText(String idText) {
		this.idText = idText;
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

	public String getDescripcionMaterial() {
		return descripcionMaterial;
	}

	public void setDescripcionMaterial(String descripcionMaterial) {
		this.descripcionMaterial = descripcionMaterial;
	}

	public String getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(String idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getIncluirEnPatronaje() {
		return incluirEnPatronaje;
	}

	public void setIncluirEnPatronaje(String incluirEnPatronaje) {
		this.incluirEnPatronaje = incluirEnPatronaje;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	public String getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(String idMarca) {
		this.idMarca = idMarca;
	}

	public String getIdClasificacion() {
		return idClasificacion;
	}

	public void setIdClasificacion(String idClasificacion) {
		this.idClasificacion = idClasificacion;
	}

	public String getNombreMaterial() {
		return nombreMaterial;
	}

	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public Long getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(Long idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Long getIdTipoMaterial() {
		return idTipoMaterial;
	}

	public void setIdTipoMaterial(Long idTipoMaterial) {
		this.idTipoMaterial = idTipoMaterial;
	}

	public Long getIdColor() {
		return idColor;
	}

	public void setIdColor(Long idColor) {
		this.idColor = idColor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualizadoPor == null) ? 0 : actualizadoPor.hashCode());
		result = prime * result + ((creadoPor == null) ? 0 : creadoPor.hashCode());
		result = prime * result + ((descripcionMaterial == null) ? 0 : descripcionMaterial.hashCode());
		result = prime * result + ((estatus == null) ? 0 : estatus.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((idAlmacen == null) ? 0 : idAlmacen.hashCode());
		result = prime * result + ((idClasificacion == null) ? 0 : idClasificacion.hashCode());
		result = prime * result + ((idColor == null) ? 0 : idColor.hashCode());
		result = prime * result + ((idMarca == null) ? 0 : idMarca.hashCode());
		result = prime * result + ((idMaterial == null) ? 0 : idMaterial.hashCode());
		result = prime * result + ((idProceso == null) ? 0 : idProceso.hashCode());
		result = prime * result + ((idText == null) ? 0 : idText.hashCode());
		result = prime * result + ((idTipoMaterial == null) ? 0 : idTipoMaterial.hashCode());
		result = prime * result + ((idUnidadMedida == null) ? 0 : idUnidadMedida.hashCode());
		result = prime * result + ((incluirEnPatronaje == null) ? 0 : incluirEnPatronaje.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((modeloProveedor == null) ? 0 : modeloProveedor.hashCode());
		result = prime * result + ((nombreMaterial == null) ? 0 : nombreMaterial.hashCode());
		result = prime * result + ((precioUnitario == null) ? 0 : precioUnitario.hashCode());
		result = prime * result + ((tamanio == null) ? 0 : tamanio.hashCode());
		result = prime * result + ((ultimaFechaModificacion == null) ? 0 : ultimaFechaModificacion.hashCode());
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
		DisenioMaterial other = (DisenioMaterial) obj;
		if (actualizadoPor == null) {
			if (other.actualizadoPor != null)
				return false;
		} else if (!actualizadoPor.equals(other.actualizadoPor))
			return false;
		if (creadoPor == null) {
			if (other.creadoPor != null)
				return false;
		} else if (!creadoPor.equals(other.creadoPor))
			return false;
		if (descripcionMaterial == null) {
			if (other.descripcionMaterial != null)
				return false;
		} else if (!descripcionMaterial.equals(other.descripcionMaterial))
			return false;
		if (estatus == null) {
			if (other.estatus != null)
				return false;
		} else if (!estatus.equals(other.estatus))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (idAlmacen == null) {
			if (other.idAlmacen != null)
				return false;
		} else if (!idAlmacen.equals(other.idAlmacen))
			return false;
		if (idClasificacion == null) {
			if (other.idClasificacion != null)
				return false;
		} else if (!idClasificacion.equals(other.idClasificacion))
			return false;
		if (idColor == null) {
			if (other.idColor != null)
				return false;
		} else if (!idColor.equals(other.idColor))
			return false;
		if (idMarca == null) {
			if (other.idMarca != null)
				return false;
		} else if (!idMarca.equals(other.idMarca))
			return false;
		if (idMaterial == null) {
			if (other.idMaterial != null)
				return false;
		} else if (!idMaterial.equals(other.idMaterial))
			return false;
		if (idProceso == null) {
			if (other.idProceso != null)
				return false;
		} else if (!idProceso.equals(other.idProceso))
			return false;
		if (idText == null) {
			if (other.idText != null)
				return false;
		} else if (!idText.equals(other.idText))
			return false;
		if (idTipoMaterial == null) {
			if (other.idTipoMaterial != null)
				return false;
		} else if (!idTipoMaterial.equals(other.idTipoMaterial))
			return false;
		if (idUnidadMedida == null) {
			if (other.idUnidadMedida != null)
				return false;
		} else if (!idUnidadMedida.equals(other.idUnidadMedida))
			return false;
		if (incluirEnPatronaje == null) {
			if (other.incluirEnPatronaje != null)
				return false;
		} else if (!incluirEnPatronaje.equals(other.incluirEnPatronaje))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (modeloProveedor == null) {
			if (other.modeloProveedor != null)
				return false;
		} else if (!modeloProveedor.equals(other.modeloProveedor))
			return false;
		if (nombreMaterial == null) {
			if (other.nombreMaterial != null)
				return false;
		} else if (!nombreMaterial.equals(other.nombreMaterial))
			return false;
		if (precioUnitario == null) {
			if (other.precioUnitario != null)
				return false;
		} else if (!precioUnitario.equals(other.precioUnitario))
			return false;
		if (tamanio == null) {
			if (other.tamanio != null)
				return false;
		} else if (!tamanio.equals(other.tamanio))
			return false;
		if (ultimaFechaModificacion == null) {
			if (other.ultimaFechaModificacion != null)
				return false;
		} else if (!ultimaFechaModificacion.equals(other.ultimaFechaModificacion))
			return false;
		return true;
	}
	
}

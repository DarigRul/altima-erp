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
@Table(name = "alt_disenio_prueba_encogimiento_lavado")
public class DisenioPruebaEncogimientoLavado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_prueba_encogimiento_lavado")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idPruebaEncogimientoLavado;
	
	@Column(name="id_operario")
	private Long idOperario;

	@Column(name="entretela_prueba_vapor")
	private String entretelaPruebaVapor;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="fecha_realizacion")
	private String fechaRealizacion;
	
	@Column(name="adherencia_prueba_vapor")
	private String adherenciaPruebaVapor;
	
	@Column(name="proveedor_prueba_vapor")
	private String proveedorPruebavapor;
	
	@Column(name="temperatura_prueba_vapor")
	private String temperaturaPruebaVapor;
	
	@Column(name="tiempo_prueba")
	private String tiempoPrueba;
	
	@Column(name="presion_prueba")
	private String presionPrueba;
	
	@Column(name="medida_inicial_hilo")
	private String medidaInicialHilo;
	
	@Column(name="medida_inicial_trama")
	private String medidaInicialTrama;
	
	@Column(name="medida_final_hilo")
	private String medidaFinalHilo;
	
	@Column(name="diferencia_medida_hilo")
	private String diferenciaMedidaHilo;
	
	@Column(name="medida_final_trama")
	private String medidaFinalTrama;
	
	@Column(name="diferencia_medida_trama")
	private String diferenciaMedidaTrama;
	
	@Column(name="observaciones_resultados")
	private String observacionesResultados;
	
	@Column(name="tipo_prueba")
	private String tipoPrueba;
	
	@Column(name="id_calidad")
	private Long idCalidad;
	
	@Column(name="estatus")
	private String estatus;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	
	
	
	
	
	public Long getIdOperario() {
		return idOperario;
	}

	public void setIdOperario(Long idOperario) {
		this.idOperario = idOperario;
	}

	public String getProveedorPruebavapor() {
		return proveedorPruebavapor;
	}

	public void setProveedorPruebavapor(String proveedorPruebavapor) {
		this.proveedorPruebavapor = proveedorPruebavapor;
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

	public String getActualizadoPor() {
		return actualizadoPor;
	}

	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public String getProveedorPruebaVapor() {
		return proveedorPruebavapor;
	}

	public void setProveedorPruebaVapor(String proveedorPruebaVapor) {
		this.proveedorPruebavapor = proveedorPruebaVapor;
	}

	public Long getIdPruebaEncogimientoLavado() {
		return idPruebaEncogimientoLavado;
	}

	public void setIdPruebaEncogimientoLavado(Long idPruebaEncogimientoLavado) {
		this.idPruebaEncogimientoLavado = idPruebaEncogimientoLavado;
	}

	public String getEntretelaPruebaVapor() {
		return entretelaPruebaVapor;
	}

	public void setEntretelaPruebaVapor(String entretelaPruebaVapor) {
		this.entretelaPruebaVapor = entretelaPruebaVapor;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(String fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getAdherenciaPruebaVapor() {
		return adherenciaPruebaVapor;
	}

	public void setAdherenciaPruebaVapor(String adherenciaPruebaVapor) {
		this.adherenciaPruebaVapor = adherenciaPruebaVapor;
	}

	public String getTemperaturaPruebaVapor() {
		return temperaturaPruebaVapor;
	}

	public void setTemperaturaPruebaVapor(String temperaturaPruebaVapor) {
		this.temperaturaPruebaVapor = temperaturaPruebaVapor;
	}

	public String getTiempoPrueba() {
		return tiempoPrueba;
	}

	public void setTiempoPrueba(String tiempoPrueba) {
		this.tiempoPrueba = tiempoPrueba;
	}

	public String getPresionPrueba() {
		return presionPrueba;
	}

	public void setPresionPrueba(String presionPrueba) {
		this.presionPrueba = presionPrueba;
	}

	public String getMedidaInicialHilo() {
		return medidaInicialHilo;
	}

	public void setMedidaInicialHilo(String medidaInicialHilo) {
		this.medidaInicialHilo = medidaInicialHilo;
	}

	public String getMedidaInicialTrama() {
		return medidaInicialTrama;
	}

	public void setMedidaInicialTrama(String medidaInicialTrama) {
		this.medidaInicialTrama = medidaInicialTrama;
	}

	public String getMedidaFinalHilo() {
		return medidaFinalHilo;
	}

	public void setMedidaFinalHilo(String medidaFinalHilo) {
		this.medidaFinalHilo = medidaFinalHilo;
	}

	public String getDiferenciaMedidaHilo() {
		return diferenciaMedidaHilo;
	}

	public void setDiferenciaMedidaHilo(String diferenciaMedidaHilo) {
		this.diferenciaMedidaHilo = diferenciaMedidaHilo;
	}

	public String getMedidaFinalTrama() {
		return medidaFinalTrama;
	}

	public void setMedidaFinalTrama(String medidaFinalTrama) {
		this.medidaFinalTrama = medidaFinalTrama;
	}

	public String getDiferenciaMedidaTrama() {
		return diferenciaMedidaTrama;
	}

	public void setDiferenciaMedidaTrama(String diferenciaMedidaTrama) {
		this.diferenciaMedidaTrama = diferenciaMedidaTrama;
	}

	public String getObservacionesResultados() {
		return observacionesResultados;
	}

	public void setObservacionesResultados(String observacionesResultados) {
		this.observacionesResultados = observacionesResultados;
	}

	public String getTipoPrueba() {
		return tipoPrueba;
	}

	public void setTipoPrueba(String tipoPrueba) {
		this.tipoPrueba = tipoPrueba;
	}

	public Long getIdCalidad() {
		return idCalidad;
	}

	public void setIdCalidad(Long idCalidad) {
		this.idCalidad = idCalidad;
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
}

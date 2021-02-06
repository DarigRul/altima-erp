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
@Table(name = "alt_produccion_consumo_real")
public class ProduccionConsumoReal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_consumo_real")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
    private Long idConsumoReal;
    
    @Column(name="id_tela")
    private Integer idTela;
    
    @Column(name="programa")
    private String programa;
    
    @Column(name="id_coordinado_prenda")
    private Integer idCoordinadoPrenda;
    
    @Column(name="consumo_real")
    private String consumoReal;
    
    @Column(name="tipo_tela")
    private String tipoTela;
    
   
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdConsumoReal() {
        return idConsumoReal;
    }

    public void setIdConsumoReal(Long idConsumoReal) {
        this.idConsumoReal = idConsumoReal;
    }

    public Integer getIdTela() {
        return idTela;
    }

    public void setIdTela(Integer idTela) {
        this.idTela = idTela;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Integer getIdCoordinadoPrenda() {
        return idCoordinadoPrenda;
    }

    public void setIdCoordinadoPrenda(Integer idCoordinadoPrenda) {
        this.idCoordinadoPrenda = idCoordinadoPrenda;
    }

    public String getConsumoReal() {
        return consumoReal;
    }

    public void setConsumoReal(String consumoReal) {
        this.consumoReal = consumoReal;
    }

    public String getTipoTela() {
        return tipoTela;
    }

    public void setTipoTela(String tipoTela) {
        this.tipoTela = tipoTela;
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
	
    
}

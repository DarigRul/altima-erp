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
@Table(name = "alt_produccion_prendas_historico")
public class ProduccionPrendaHistorico implements Serializable{
    private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_prenda_historico")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idPrendaHistorico;
	
	@Column(name="id_prenda")
	private Long idPrenda;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="comentario")
	private String comentario;
	
	@Column(name="fecha")
	private String fecha;

    @Column(name="creado_por")
	private String creadoPor;

    public Long getIdPrendaHistorico() {
        return idPrendaHistorico;
    }

    public void setIdPrendaHistorico(Long idPrendaHistorico) {
        this.idPrendaHistorico = idPrendaHistorico;
    }

    public Long getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(Long idPrenda) {
        this.idPrenda = idPrenda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }
	
}

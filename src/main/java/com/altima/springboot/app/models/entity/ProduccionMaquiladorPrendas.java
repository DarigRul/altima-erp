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
@Table(name = "alt_produccion_maquilador_fprenda")
public class ProduccionMaquiladorPrendas implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id_maquilador_fprenda")
    private Long idMaquiladorProceso;

    @Column(name = "id_maquilador")
    private Long idMaquilador;

    @Column(name = "id_familia_prenda")
    private Long idFamiliaPrenda;

    @Column(name = "produccion_maxima")
    private int produccionMaxima;

    public Long getIdMaquiladorProceso() {
        return idMaquiladorProceso;
    }

    public void setIdMaquiladorProceso(Long idMaquiladorProceso) {
        this.idMaquiladorProceso = idMaquiladorProceso;
    }

    public Long getIdMaquilador() {
        return idMaquilador;
    }

    public void setIdMaquilador(Long idMaquilador) {
        this.idMaquilador = idMaquilador;
    }

    public Long getIdFamiliaPrenda() {
        return idFamiliaPrenda;
    }

    public void setIdFamiliaPrenda(Long idFamiliaPrenda) {
        this.idFamiliaPrenda = idFamiliaPrenda;
    }

    public int getProduccionMaxima() {
        return produccionMaxima;
    }

    public void setProduccionMaxima(int produccionMaxima) {
        this.produccionMaxima = produccionMaxima;
    }

    
}

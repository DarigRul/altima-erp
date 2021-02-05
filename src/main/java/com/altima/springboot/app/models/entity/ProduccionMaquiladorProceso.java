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
@Table(name = "alt_produccion_maquilador_proceso")
public class ProduccionMaquiladorProceso implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id_maquilador_proceso")
    private Long idMaquiladorProceso;

    @Column(name = "id_maquilador")
    private Long idMaquilador;

    @Column(name = "id_proceso")
    private Long idProceso;

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

    public Long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Long idProceso) {
        this.idProceso = idProceso;
    }

}

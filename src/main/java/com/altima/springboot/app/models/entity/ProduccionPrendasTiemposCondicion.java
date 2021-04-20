package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alt_produccion_prendas_tiempos_condicion")
public class ProduccionPrendasTiemposCondicion implements Serializable{
    
    private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_familia_prenda")
	private Long idFamiliaPrenda;
	
	@Column(name="familia_prenda")
    private String familiaPrenda;

    public Long getIdFamiliaPrenda() {
        return idFamiliaPrenda;
    }

    public void setIdFamiliaPrenda(Long idFamiliaPrenda) {
        this.idFamiliaPrenda = idFamiliaPrenda;
    }

    public String getFamiliaPrenda() {
        return familiaPrenda;
    }

    public void setFamiliaPrenda(String familiaPrenda) {
        this.familiaPrenda = familiaPrenda;
    }

}

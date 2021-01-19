package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SelectPedidoInformacionDto implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long idPedidoInformacion;
    private String idText;

    public Long getIdPedidoInformacion() {
        return idPedidoInformacion;
    }

    public void setIdPedidoInformacion(Long idPedidoInformacion) {
        this.idPedidoInformacion = idPedidoInformacion;
    }

    public String getIdText() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText = idText;
    }
}

package com.altima.springboot.app.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PedidoInformacionDTO {

    @Id
    private Long idPedidoInformacion;
    private String idText;
    private String fechaEntrega;
    private String cliente;

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

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    
    
}

package com.altima.springboot.app.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TelaFaltanteListDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long idTelaFaltante;
    private String idTextPedido;
    private String idTextTela;
    private String cliente;
    private String fechaEntrega;
    private String agente;
    private float cantidad;
    private String fechaOrdenCompra;
    private String folioOrdenCompra;
	private String fechaPomesa;
    private String estatusCompra;
    private String estatusComercial;
    private String nombreTela;
    private String idTela;
    private String idProveedor;
    private String nombreProveedor;

    public Long getIdTelaFaltante() {
        return idTelaFaltante;
    }

    public void setIdTelaFaltante(Long idTelaFaltante) {
        this.idTelaFaltante = idTelaFaltante;
    }

    public String getIdTextPedido() {
        return idTextPedido;
    }

    public void setIdTextPedido(String idTextPedido) {
        this.idTextPedido = idTextPedido;
    }

    public String getIdTextTela() {
        return idTextTela;
    }

    public void setIdTextTela(String idTextTela) {
        this.idTextTela = idTextTela;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    public void setFechaOrdenCompra(String fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    public String getFolioOrdenCompra() {
        return folioOrdenCompra;
    }

    public void setFolioOrdenCompra(String folioOrdenCompra) {
        this.folioOrdenCompra = folioOrdenCompra;
    }

    public String getFechaPomesa() {
        return fechaPomesa;
    }

    public void setFechaPomesa(String fechaPomesa) {
        this.fechaPomesa = fechaPomesa;
    }

    public String getEstatusCompra() {
        return estatusCompra;
    }

    public void setEstatusCompra(String estatusCompra) {
        this.estatusCompra = estatusCompra;
    }

    public String getEstatusComercial() {
        return estatusComercial;
    }

    public void setEstatusComercial(String estatusComercial) {
        this.estatusComercial = estatusComercial;
    }

    public String getNombreTela() {
        return nombreTela;
    }

    public void setNombreTela(String nombreTela) {
        this.nombreTela = nombreTela;
    }

    public String getIdTela() {
        return idTela;
    }

    public void setIdTela(String idTela) {
        this.idTela = idTela;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    
}

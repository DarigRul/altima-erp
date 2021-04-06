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
@Table(name = "alt_servicio_cliente_recepcion_devolucion_historico")
public class ServicioClienteRecepcionDevolucionHistorico implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_historico")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idHistorico;

    @Column(name="id_recepcion_devolucion")
	private Long idRecepcionDevolucion;

    @Column(name="cantidad")
	private String cantidad;

    @Column(name="movimiento")
	private String movimiento;

    @Column(name="fecha")
	private String fecha;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Long getIdRecepcionDevolucion() {
        return idRecepcionDevolucion;
    }

    public void setIdRecepcionDevolucion(Long idRecepcionDevolucion) {
        this.idRecepcionDevolucion = idRecepcionDevolucion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
}

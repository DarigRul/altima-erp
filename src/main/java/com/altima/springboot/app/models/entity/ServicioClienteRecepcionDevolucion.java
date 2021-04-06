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
@Table(name = "alt_servicio_cliente_recepcion_devolucion")
public class ServicioClienteRecepcionDevolucion  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_recepcion_devolucion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="native",strategy="native")
	private Long idRecepcionDevolucion;

    @Column(name="id_op")
	private String idOp;

    @Column(name="id_maquilero")
	private String idMaquilero;

    @Column(name="recibida")
	private String recibida;

    @Column(name="pendiente")
	private String pendiente;

    @Column(name="dev")
	private String dev;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getIdRecepcionDevolucion() {
        return idRecepcionDevolucion;
    }

    public void setIdRecepcionDevolucion(Long idRecepcionDevolucion) {
        this.idRecepcionDevolucion = idRecepcionDevolucion;
    }

    public String getIdOp() {
        return idOp;
    }

    public void setIdOp(String idOp) {
        this.idOp = idOp;
    }

    public String getRecibida() {
        return recibida;
    }

    public void setRecibida(String recibida) {
        this.recibida = recibida;
    }

    public String getPendiente() {
        return pendiente;
    }

    public void setPendiente(String pendiente) {
        this.pendiente = pendiente;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getIdMaquilero() {
        return idMaquilero;
    }

    public void setIdMaquilero(String idMaquilero) {
        this.idMaquilero = idMaquilero;
    }

    
    

}
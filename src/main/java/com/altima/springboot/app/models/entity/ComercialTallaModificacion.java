package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedNativeQueries;
import javax.persistence.Table;

import com.altima.springboot.app.dto.ModificacionDto;

import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;
import org.hibernate.annotations.GenericGenerator;

@SqlResultSetMapping(name = "modificaciones_mapping", classes = {
        @ConstructorResult(targetClass = ModificacionDto.class, columns = {
                @ColumnResult(name = "idModificacion", type = Long.class),
                @ColumnResult(name = "especificacion", type = String.class),
                @ColumnResult(name = "pulgadas", type = String.class), }) })
@NamedNativeQueries({
        @NamedNativeQuery(name = "modificaciones", query = "SELECT actm.id idModificacion,ascl.nombre_lookup especificacion,actm.pulgadas FROM alt_comercial_talla_modificacion actm INNER JOIN alt_servicio_cliente_lookup ascl ON ascl.id_lookup=actm.id_especificacion WHERE actm.id_concentrado_talla=:idConcentradoTalla", resultSetMapping = "modificaciones_mapping"), })
@Entity
@Table(name = "alt_comercial_talla_modificacion")
public class ComercialTallaModificacion implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "id_concentrado_talla")
    private Long idConcentradoTalla;

    @Column(name = "id_especificacion")
    private Long idEspecificacion;

    @Column(name = "pulgadas")
    private Float pulgadas;

    @Column(name = "creado_por")
    private String creadoPor;

    @Column(name = "actualizado_por")
    private String actualizadoPor;

    @Column(name = "ultima_fecha_modificacion")
    private String ultimaFechaModificacion;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdConcentradoTalla() {
        return idConcentradoTalla;
    }

    public void setIdConcentradoTalla(Long idConcentradoTalla) {
        this.idConcentradoTalla = idConcentradoTalla;
    }

    public Long getIdEspecificacion() {
        return idEspecificacion;
    }

    public void setIdEspecificacion(Long idEspecificacion) {
        this.idEspecificacion = idEspecificacion;
    }

    public Float getPulgadas() {
        return pulgadas;
    }

    public void setPulgadas(Float pulgadas) {
        this.pulgadas = pulgadas;
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

    public String getUltimaFechaModificacion() {
        return ultimaFechaModificacion;
    }

    public void setUltimaFechaModificacion(String ultimaFechaModificacion) {
        this.ultimaFechaModificacion = ultimaFechaModificacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "ComercialTallaModificacion [actualizadoPor=" + actualizadoPor + ", creadoPor=" + creadoPor
                + ", fechaCreacion=" + fechaCreacion + ", id=" + id + ", idConcentradoTalla=" + idConcentradoTalla
                + ", idEspecificacion=" + idEspecificacion + ", pulgadas=" + pulgadas + ", ultimaFechaModificacion="
                + ultimaFechaModificacion + "]";
    }

}

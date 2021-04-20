package com.altima.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.altima.springboot.app.dto.TiempoCantidadProcesoDto;
import com.altima.springboot.app.dto.TiemposProcesosDto;

import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.ColumnResult;

@Entity
@NamedNativeQuery(
    name = "find_tiempo_familia_prenda",
    query ="CALL `alt_pr_tiempos_familia_prenda`();",
    resultSetMapping = "tiempos_procesos_dto"
)
@SqlResultSetMapping(
    name = "tiempos_procesos_dto",
    classes = @ConstructorResult(
        targetClass = TiemposProcesosDto.class,
        columns = {
            @ColumnResult(name = "idLookup", type = Long.class),
            @ColumnResult(name = "idTiempoFamiliaPrenda", type = Long.class),
            @ColumnResult(name = "familiaPrenda", type = String.class),
			@ColumnResult(name = "tiempoTalla", type = int.class),
            @ColumnResult(name = "tiempoPrendaLisa", type = int.class),
            @ColumnResult(name = "tiempoPrendaCuadros", type = int.class),
			@ColumnResult(name = "tiempoPrendaFantasia", type = int.class),
            @ColumnResult(name = "tiempoPrendaRayasVerticales", type = int.class),
			@ColumnResult(name = "tiempoPrendaRayasHorizontales", type = int.class),
			@ColumnResult(name = "tiempoRefilado", type = int.class),
        }
    )
)
@NamedNativeQuery(
    name = "find_tiempo_cantidad_procesos",
    query ="SELECT COUNT(DISTINCT avtep.id_talla,avtep.id_largo) cantidadTallas,(SELECT sum(resultdo) FROM (SELECT (count(DISTINCT avtep7.id_talla,apl.nombre_lookup)-1)*3 AS resultdo FROM alt_view_tiempos_empalme_produccion avtep7 INNER JOIN alt_produccion_tiempos_familia_prenda aptfp ON avtep7.id_familia_prenda=aptfp.id_familia_prenda INNER JOIN alt_produccion_lookup apl ON apl.id_lookup=avtep7.id_largo WHERE 1=1 AND avtep7.id_coordinado_prenda IN (10,2) AND (apl.nombre_lookup LIKE '%R%' OR apl.nombre_lookup LIKE '%C%') GROUP BY id_talla) AS t)+(SELECT sum(resultdo) FROM (SELECT (count(DISTINCT avtep7.id_talla,apl.nombre_lookup)-1)*3 AS resultdo FROM alt_view_tiempos_empalme_produccion avtep7 INNER JOIN alt_produccion_tiempos_familia_prenda aptfp ON avtep7.id_familia_prenda=aptfp.id_familia_prenda INNER JOIN alt_produccion_lookup apl ON apl.id_lookup=avtep7.id_largo WHERE 1=1 AND avtep7.id_coordinado_prenda IN (10,2) AND apl.nombre_lookup LIKE '%L%' GROUP BY id_talla) AS t) tiempoRefilado,(SELECT COUNT(DISTINCT (avtep7.id_talla)) FROM alt_view_tiempos_empalme_produccion avtep7 INNER JOIN alt_produccion_tiempos_familia_prenda aptfp ON avtep7.id_familia_prenda=aptfp.id_familia_prenda INNER JOIN alt_produccion_lookup apl ON apl.id_lookup=avtep7.id_largo WHERE 1=1 AND avtep7.id_coordinado_prenda IN (10,2) AND (apl.nombre_lookup LIKE '%R%' OR apl.nombre_lookup LIKE '%C%'))+(SELECT COUNT(DISTINCT (avtep.id_talla)) FROM alt_view_tiempos_empalme_produccion avtep INNER JOIN alt_produccion_tiempos_familia_prenda aptfp ON avtep.id_familia_prenda=aptfp.id_familia_prenda INNER JOIN alt_produccion_lookup apl ON apl.id_lookup=avtep.id_largo WHERE 1=1 AND avtep.id_coordinado_prenda IN (10,2) AND apl.nombre_lookup LIKE '%L%') cantidadTallasAgrupadas,aptfp.tiempo_talla tiempoTalla,aptfp.tiempo_prenda_lisa tiempoPrendaLisa,aptfp.tiempo_prenda_cuadros tiempoPrendaCuadros,aptfp.tiempo_prenda_fantasia tiempoPrendaFantasia,aptfp.tiempo_prenda_rayas_horizontales tiempoPrendaRayasHorizontales,aptfp.tiempo_prenda_rayas_verticales tiempoPrendaRayasVerticales,(SELECT IFNULL(SUM(avtep2.cantidad),0) FROM alt_view_tiempos_empalme_produccion avtep2 WHERE avtep2.id_coordinado_prenda IN (:idCoordinadoPrenda) AND avtep2.estampado LIKE 'Liso') cantidadLiso,(SELECT IFNULL(SUM(avtep3.cantidad),0) FROM alt_view_tiempos_empalme_produccion avtep3 WHERE avtep3.id_coordinado_prenda IN (:idCoordinadoPrenda) AND avtep3.estampado LIKE 'Cuadros') cantidadCuadros,(SELECT IFNULL(SUM(avtep4.cantidad),0) FROM alt_view_tiempos_empalme_produccion avtep4 WHERE avtep4.id_coordinado_prenda IN (:idCoordinadoPrenda) AND avtep4.estampado LIKE 'Fantasia') cantidadFantasia,(SELECT IFNULL(SUM(avtep5.cantidad),0) FROM alt_view_tiempos_empalme_produccion avtep5 WHERE avtep5.id_coordinado_prenda IN (:idCoordinadoPrenda) AND avtep5.estampado LIKE 'Rayas horizontales') cantidadRayasHorizontales,(SELECT IFNULL(SUM(avtep6.cantidad),0) FROM alt_view_tiempos_empalme_produccion avtep6 WHERE avtep6.id_coordinado_prenda IN (:idCoordinadoPrenda) AND avtep6.estampado LIKE 'Rayas verticales') cantidadRayasVerticales FROM alt_view_tiempos_empalme_produccion avtep INNER JOIN alt_produccion_tiempos_familia_prenda aptfp ON avtep.id_familia_prenda=aptfp.id_familia_prenda WHERE avtep.id_coordinado_prenda IN (:idCoordinadoPrenda)",
    resultSetMapping = "tiempo_cantidad_dto"
)
@SqlResultSetMapping(
    name = "tiempo_cantidad_dto",
    classes = @ConstructorResult(
        targetClass = TiempoCantidadProcesoDto.class,
        columns = {
			@ColumnResult(name = "cantidadTallas", type = int.class),
            @ColumnResult(name = "tiempoTalla", type = int.class),
            @ColumnResult(name = "tiempoPrendaLisa", type = int.class),
			@ColumnResult(name = "tiempoPrendaCuadros", type = int.class),
            @ColumnResult(name = "tiempoPrendaFantasia", type = int.class),
            @ColumnResult(name = "tiempoPrendaRayasHorizontales", type = int.class),
			@ColumnResult(name = "tiempoPrendaRayasVerticales", type = int.class),
            @ColumnResult(name = "cantidadLiso", type = int.class),
            @ColumnResult(name = "cantidadCuadros", type = int.class),
			@ColumnResult(name = "cantidadFantasia", type = int.class),
            @ColumnResult(name = "cantidadRayasHorizontales", type = int.class),
            @ColumnResult(name = "cantidadRayasVerticales", type = int.class),
            @ColumnResult(name = "cantidadTallasAgrupadas", type = int.class),
            @ColumnResult(name = "tiempoRefilado", type = int.class),
        }
    )
)
@Table(name = "alt_produccion_tiempos_familia_prenda")
public class ProduccionTiempoFamiliaPrenda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_tiempo_familia_prenda")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long idTiempoFamiliaPrenda;
	
	@Column(name="id_familia_prenda")
	private int idFamiliaPrenda;

	@Column(name="tiempo_talla")
	private int tiempoTalla;

	@Column(name="tiempo_prenda_lisa")
	private int tiempoPrendaLisa;

	@Column(name="tiempo_prenda_cuadros")
    private int tiempoPrendaCuadros;

	@Column(name="tiempo_prenda_fantasia")
    private int tiempoPrendaFantasia;

	@Column(name="tiempo_prenda_rayas_verticales")
    private int tiempoPrendaRayasVerticales;

	@Column(name="tiempo_prenda_rayas_horizontales")
    private int tiempoPrendaRayasHorizontales;

	@Column(name="tiempo_refilado")
    private int tiempoRefilado;

	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="actualizado_por")
	private String actualizadoPor;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@Column(name="ultima_fecha_modificacion")
	private String ultimaFechaModificacion;

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

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUltimaFechaModificacion() {
		return ultimaFechaModificacion;
	}

	public void setUltimaFechaModificacion(String ultimaFechaModificacion) {
		this.ultimaFechaModificacion = ultimaFechaModificacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdTiempoFamiliaPrenda() {
		return idTiempoFamiliaPrenda;
	}

	public void setIdTiempoFamiliaPrenda(Long idTiempoFamiliaPrenda) {
		this.idTiempoFamiliaPrenda = idTiempoFamiliaPrenda;
	}

	public int getTiempoTalla() {
		return tiempoTalla;
	}

	public void setTiempoTalla(int tiempoTalla) {
		this.tiempoTalla = tiempoTalla;
	}

	public int getTiempoPrendaLisa() {
		return tiempoPrendaLisa;
	}

	public void setTiempoPrendaLisa(int tiempoPrendaLisa) {
		this.tiempoPrendaLisa = tiempoPrendaLisa;
	}

	public int getIdFamiliaPrenda() {
		return idFamiliaPrenda;
	}

	public void setIdFamiliaPrenda(int idFamiliaPrenda) {
		this.idFamiliaPrenda = idFamiliaPrenda;
	}

	public int getTiempoPrendaCuadros() {
		return tiempoPrendaCuadros;
	}

	public void setTiempoPrendaCuadros(int tiempoPrendaCuadros) {
		this.tiempoPrendaCuadros = tiempoPrendaCuadros;
	}

	public int getTiempoPrendaFantasia() {
		return tiempoPrendaFantasia;
	}

	public void setTiempoPrendaFantasia(int tiempoPrendaFantasia) {
		this.tiempoPrendaFantasia = tiempoPrendaFantasia;
	}

	public int getTiempoPrendaRayasVerticales() {
		return tiempoPrendaRayasVerticales;
	}

	public void setTiempoPrendaRayasVerticales(int tiempoPrendaRayasVerticales) {
		this.tiempoPrendaRayasVerticales = tiempoPrendaRayasVerticales;
	}

	public int getTiempoPrendaRayasHorizontales() {
		return tiempoPrendaRayasHorizontales;
	}

	public void setTiempoPrendaRayasHorizontales(int tiempoPrendaRayasHorizontales) {
		this.tiempoPrendaRayasHorizontales = tiempoPrendaRayasHorizontales;
	}

	public int getTiempoRefilado() {
		return tiempoRefilado;
	}

	public void setTiempoRefilado(int tiempoRefilado) {
		this.tiempoRefilado = tiempoRefilado;
	}

}

package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.dto.TiempoCantidadProcesoDto;
import com.altima.springboot.app.dto.TiemposProcesosDto;
import com.altima.springboot.app.models.entity.ProduccionTiempoFamiliaPrenda;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionTiempoFamiliaPrendaRepository extends CrudRepository<ProduccionTiempoFamiliaPrenda,Long>{

    @Query(
        name =  "find_tiempo_familia_prenda", 
        nativeQuery = true)
    List<TiemposProcesosDto> findTiempoFamiliaPrenda();  
    
    @Query(
        name =  "find_tiempo_cantidad_procesos", 
        nativeQuery = true)
    TiempoCantidadProcesoDto findTiempoCantidadFamiliaPrenda(List<String> idCoordinadoPrenda);  

    @Query(
        value =  "SELECT COUNT(*) FROM alt_disenio_prenda adp WHERE adp.id_prenda=:idPrenda AND adp.id_familia_prenda IN (SELECT apptc.id_familia_prenda FROM alt_produccion_prendas_tiempos_condicion apptc)", 
        nativeQuery = true)
    Integer findPrendaCondicion(Long idPrenda);  
}

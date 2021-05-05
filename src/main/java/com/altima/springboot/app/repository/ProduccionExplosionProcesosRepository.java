package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;

@Repository
public interface ProduccionExplosionProcesosRepository extends CrudRepository<ProduccionExplosionProcesos, Long> {

    @Modifying
    @Query(value = "UPDATE `alt_produccion_explosion_prendas` SET `realizo`=:realizo,`fecha_inicio`=:fechaInicioModal,`fecha_fin`=:fechaFinModal WHERE `id_explosion_proceso`=:idExplosionProceso", nativeQuery = true)
    void updateRealizoExplosion(Long realizo, Long idExplosionProceso,String fechaInicioModal,String fechaFinModal);

}
